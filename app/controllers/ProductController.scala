package controllers

import models.Product
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import repositories.{CategoryRepo, ProductRepo, PromotionRepo, RateRepo}

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductController @Inject() (
    productRepo: ProductRepo,
    categoryRepo: CategoryRepo,
    rateRepo: RateRepo,
    promotionRepo: PromotionRepo,
    cc: MessagesControllerComponents
)(implicit ec: ExecutionContext)
    extends MessagesAbstractController(cc) {

  val productForm: Form[CreateProductForm] = Form {
    mapping(
      "categoryId" -> number,
      "rateId" -> number,
      "promotionId" -> number,
      "price" -> number
    )(CreateProductForm.apply)(CreateProductForm.unapply)
  }

  val updateProductForm: Form[UpdateProductForm] = Form {
    mapping(
      "id" -> number,
      "categoryId" -> number,
      "rateId" -> number,
      "promotionId" -> number,
      "price" -> number
    )(UpdateProductForm.apply)(UpdateProductForm.unapply)
  }

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      productRepo.list().map { product =>
        Ok(views.html.product(product))
      }
    }

  def indexJSON(): Action[AnyContent] =
    Action.async {
      val product = productRepo.list()
      product.map { product =>
        Ok(Json.toJson(product))
      }
    }

  def create(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val product = productRepo.list()
      product.map(_ => Ok(views.html.productCreate(productForm)))
    }

  def createJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Product]
        .map { product =>
          productRepo
            .create(
              product.categoryId,
              product.rateId,
              product.promotionId,
              product.price
            )
            .map { res =>
              Ok(Json.toJson(res))
            }
        }
        .getOrElse(Future.successful(BadRequest("data error")))
    }

  def createProductHandler(): Action[AnyContent] =
    Action async { implicit request =>
      productForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.productCreate(errorForm))
          )
        },
        product => {
          productRepo
            .create(
              product.categoryId,
              product.rateId,
              product.promotionId,
              product.price
            )
            .map { _ =>
              Redirect("/api/Product")
            }
        }
      )
    }

  def update(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val product = productRepo.get(id)
      product.map(product => {
        val form = updateProductForm.fill(
          UpdateProductForm(
            product.get.id,
            product.get.categoryId,
            product.get.rateId,
            product.get.promotionId,
            product.get.price
          )
        )
        Ok(views.html.productUpdate(form))
      })
    }

  def updateJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Product]
        .map { product =>
          productRepo.update(product.id, product).map { res =>
            Ok(Json.toJson(res))
          }
        }
        .getOrElse(Future.successful(BadRequest("invalid json")))
    }

  def updateProductHandler(): Action[AnyContent] =
    Action.async { implicit request =>
      updateProductForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.productUpdate(errorForm))
          )
        },
        product => {
          productRepo
            .update(
              product.id,
              Product(
                product.id,
                product.categoryId,
                product.rateId,
                product.promotionId,
                product.price
              )
            )
            .map { _ =>
              Redirect("/api/Product")
            }

        }
      )
    }

  def delete(id: Int) =
    Action {
      productRepo.delete(id)
      Redirect("/api/Product")
    }

  def deleteJSON(id: Int): Action[AnyContent] =
    Action.async {
      productRepo.delete(id).map { res =>
        Ok(Json.toJson(res))
      }
    }

}

case class CreateProductForm(
    categoryId: Int,
    rateId: Int,
    promotionId: Int,
    price: Int
)
case class UpdateProductForm(
    id: Int,
    categoryId: Int,
    rateId: Int,
    promotionId: Int,
    price: Int
)
