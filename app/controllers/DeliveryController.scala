package controllers

import models.Delivery
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import repositories.DeliveryRepo

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DeliveryController @Inject() (
    deliveryRepo: DeliveryRepo,
    cc: MessagesControllerComponents
)(implicit
    ec: ExecutionContext
) extends MessagesAbstractController(cc) {

  val deliveryForm: Form[CreateDeliveryForm] = Form {
    mapping(
      "userId" -> number,
      "name" -> nonEmptyText
    )(CreateDeliveryForm.apply)(CreateDeliveryForm.unapply)
  }

  val updateDeliveryForm: Form[UpdateDeliveryForm] = Form {
    mapping(
      "id" -> number,
      "userId" -> number,
      "name" -> nonEmptyText
    )(UpdateDeliveryForm.apply)(UpdateDeliveryForm.unapply)
  }

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      deliveryRepo.list().map { delivery =>
        Ok(views.html.delivery(delivery))
      }
    }

  def indexJSON(): Action[AnyContent] =
    Action.async {
      val delivery = deliveryRepo.list()
      delivery.map { delivery =>
        Ok(Json.toJson(delivery))
      }
    }

  def create(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val delivery = deliveryRepo.list()
      delivery.map(_ => Ok(views.html.deliveryCreate(deliveryForm)))
    }

  def createJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Delivery]
        .map { delivery =>
          deliveryRepo
            .create(delivery.userId, delivery.name)
            .map { res =>
              Ok(Json.toJson(res))
            }
        }
        .getOrElse(Future.successful(BadRequest("data error")))
    }

  def createDeliveryHandler(): Action[AnyContent] =
    Action async { implicit request =>
      deliveryForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.deliveryCreate(errorForm))
          )
        },
        delivery => {
          deliveryRepo
            .create(delivery.userId, delivery.name)
            .map { _ =>
              Redirect("/api/Delivery")
            }
        }
      )
    }

  def update(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val delivery = deliveryRepo.get(id)
      delivery.map(delivery => {
        val form = updateDeliveryForm.fill(
          UpdateDeliveryForm(
            delivery.get.id,
            delivery.get.userId,
            delivery.get.name
          )
        )
        Ok(views.html.deliveryUpdate(form))
      })
    }

  def updateJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Delivery]
        .map { delivery =>
          deliveryRepo.update(delivery.id, delivery).map { res =>
            Ok(Json.toJson(res))
          }
        }
        .getOrElse(Future.successful(BadRequest("invalid json")))
    }

  def updateDeliveryHandler(): Action[AnyContent] =
    Action.async { implicit request =>
      updateDeliveryForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.deliveryUpdate(errorForm))
          )
        },
        delivery => {
          deliveryRepo
            .update(
              delivery.id,
              Delivery(
                delivery.id,
                delivery.userId,
                delivery.name
              )
            )
            .map { _ =>
              Redirect("/api/Delivery")
            }

        }
      )
    }

  def delete(id: Int) =
    Action {
      deliveryRepo.delete(id)
      Redirect("/api/Delivery")
    }

  def deleteJSON(id: Int): Action[AnyContent] =
    Action.async {
      deliveryRepo.delete(id).map { res =>
        Ok(Json.toJson(res))
      }
    }

}

case class CreateDeliveryForm(
    userId: Int,
    name: String
)
case class UpdateDeliveryForm(
    userId: Int,
    id: Int,
    name: String
)
