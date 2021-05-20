package controllers

import models.{Discount}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import repositories.DiscountRepo

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DiscountController @Inject() (
    discountRepo: DiscountRepo,
    cc: MessagesControllerComponents
)(implicit
    ec: ExecutionContext
) extends MessagesAbstractController(cc) {

  val discountForm: Form[CreateDiscountForm] = Form {
    mapping(
      "userId" -> number,
      "value" -> number
    )(CreateDiscountForm.apply)(CreateDiscountForm.unapply)
  }

  val updateDiscountForm: Form[UpdateDiscountForm] = Form {
    mapping(
      "id" -> number,
      "userId" -> number,
      "value" -> number
    )(UpdateDiscountForm.apply)(UpdateDiscountForm.unapply)
  }

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      discountRepo.list().map { discount =>
        Ok(views.html.discount(discount))
      }
    }

  def indexJSON(): Action[AnyContent] =
    Action.async {
      val discount = discountRepo.list()
      discount.map { discount =>
        Ok(Json.toJson(discount))
      }
    }

  def create(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val discount = discountRepo.list()
      discount.map(_ => Ok(views.html.discountCreate(discountForm)))
    }

  def createJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Discount]
        .map { discount =>
          discountRepo
            .create(discount.userId, discount.value)
            .map { res =>
              Ok(Json.toJson(res))
            }
        }
        .getOrElse(Future.successful(BadRequest("data error")))
    }

  def createDiscountHandler(): Action[AnyContent] =
    Action async { implicit request =>
      discountForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.discountCreate(errorForm))
          )
        },
        discount => {
          discountRepo
            .create(discount.userId, discount.value)
            .map { _ =>
              Redirect("/api/Discount")
            }
        }
      )
    }

  def update(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val discount = discountRepo.get(id)
      discount.map(discount => {
        val form = updateDiscountForm.fill(
          UpdateDiscountForm(
            discount.get.id,
            discount.get.userId,
            discount.get.value
          )
        )
        Ok(views.html.discountUpdate(form))
      })
    }

  def updateJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Discount]
        .map { discount =>
          discountRepo.update(discount.id, discount).map { res =>
            Ok(Json.toJson(res))
          }
        }
        .getOrElse(Future.successful(BadRequest("invalid json")))
    }

  def updateDiscountHandler(): Action[AnyContent] =
    Action.async { implicit request =>
      updateDiscountForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.discountUpdate(errorForm))
          )
        },
        discount => {
          discountRepo
            .update(
              discount.id,
              Discount(
                discount.id,
                discount.userId,
                discount.value
              )
            )
            .map { _ =>
              Redirect("/api/Discount")
            }

        }
      )
    }

  def delete(id: Int) =
    Action {
      discountRepo.delete(id)
      Redirect("/api/Discount")
    }

  def deleteJSON(id: Int): Action[AnyContent] =
    Action.async {
      discountRepo.delete(id).map { res =>
        Ok(Json.toJson(res))
      }
    }

}

case class CreateDiscountForm(
    userId: Int,
    value: Int
)
case class UpdateDiscountForm(
    userId: Int,
    id: Int,
    value: Int
)
