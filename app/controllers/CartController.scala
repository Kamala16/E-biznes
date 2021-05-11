package controllers

import models.{Cart, Discount, Product, User}
import play.api.mvc._
import repositories.{CartRepo, DiscountRepo, ProductRepo, UserRepo}

import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class CartController @Inject() (
    cartRepo: CartRepo,
    userRepo: UserRepo,
    productRepo: ProductRepo,
    discountRepo: DiscountRepo,
    cc: MessagesControllerComponents
)(implicit
    ec: ExecutionContext
) extends MessagesAbstractController(cc) {

  val cartForm: Form[CreateCartForm] = Form {
    mapping(
      "userId" -> number,
      "productId" -> number,
      "discountId" -> number,
      "price" -> number
    )(CreateCartForm.apply)(CreateCartForm.unapply)
  }

  val updateCartForm: Form[UpdateCartForm] = Form {
    mapping(
      "id" -> number,
      "userId" -> number,
      "productId" -> number,
      "discountId" -> number,
      "price" -> number
    )(UpdateCartForm.apply)(UpdateCartForm.unapply)
  }

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      cartRepo.list().map { cart =>
        Ok(views.html.cart(cart))
      }
    }

  def indexJSON(): Action[AnyContent] =
    Action.async {
      val cart = cartRepo.list()
      cart.map { cart =>
        Ok(Json.toJson(cart))
      }
    }

  def create(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val cart = cartRepo.list()
      cart.map(_ => Ok(views.html.cartCreate(cartForm)))
    }

  def createJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Cart]
        .map { cart =>
          cartRepo
            .create(cart.userId, cart.productId, cart.discountId, cart.price)
            .map { res =>
              Ok(Json.toJson(res))
            }
        }
        .getOrElse(Future.successful(BadRequest("data error")))
    }

  def createCartHandler(): Action[AnyContent] =
    Action async { implicit request =>
      cartForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.cartCreate(errorForm))
          )
        },
        cart => {
          cartRepo
            .create(cart.userId, cart.productId, cart.discountId, cart.price)
            .map { _ =>
              Redirect("/api/Cart")
            }
        }
      )
    }

  def update(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val cart = cartRepo.get(id)
      cart.map(cart => {
        val form = updateCartForm.fill(
          UpdateCartForm(
            cart.get.id,
            cart.get.userId,
            cart.get.productId,
            cart.get.discountId,
            cart.get.price
          )
        )
        Ok(views.html.cartUpdate(form))
      })
    }

  def updateJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Cart]
        .map { cart =>
          cartRepo.update(cart.id, cart).map { res =>
            Ok(Json.toJson(res))
          }
        }
        .getOrElse(Future.successful(BadRequest("invalid json")))
    }

  def updateCartHandler(): Action[AnyContent] =
    Action.async { implicit request =>
      updateCartForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.cartUpdate(errorForm))
          )
        },
        cart => {
          cartRepo
            .update(
              cart.id,
              Cart(
                cart.id,
                cart.userId,
                cart.productId,
                cart.discountId,
                cart.price
              )
            )
            .map { _ =>
              Redirect("/api/Cart")
            }

        }
      )
    }

  def delete(id: Int) =
    Action {
      cartRepo.delete(id)
      Redirect("/api/Cart")
    }

  def deleteJSON(id: Int): Action[AnyContent] =
    Action.async {
      cartRepo.delete(id).map { res =>
        Ok(Json.toJson(res))
      }
    }

}

case class CreateCartForm(
    userId: Int,
    productId: Int,
    discountId: Int,
    price: Int
)
case class UpdateCartForm(
    id: Int,
    userId: Int,
    productId: Int,
    discountId: Int,
    price: Int
)
