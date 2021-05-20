package controllers

import models.{Payment}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}

import javax.inject._
import play.api.mvc._
import repositories.PaymentRepo

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentController @Inject() (
    paymentRepo: PaymentRepo,
    cc: MessagesControllerComponents
)(implicit ec: ExecutionContext)
    extends MessagesAbstractController(cc) {

  val paymentForm: Form[CreatePaymentForm] = Form {
    mapping(
      "name" -> nonEmptyText
    )(CreatePaymentForm.apply)(CreatePaymentForm.unapply)
  }

  val updatePaymentForm: Form[UpdatePaymentForm] = Form {
    mapping(
      "id" -> number,
      "name" -> nonEmptyText
    )(UpdatePaymentForm.apply)(UpdatePaymentForm.unapply)
  }

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      paymentRepo.list().map { payment =>
        Ok(views.html.payment(payment))
      }
    }

  def indexJSON(): Action[AnyContent] =
    Action.async {
      val payment = paymentRepo.list()
      payment.map { payment =>
        Ok(Json.toJson(payment))
      }
    }

  def create(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val payment = paymentRepo.list()
      payment.map(_ => Ok(views.html.paymentCreate(paymentForm)))
    }

  def createJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Payment]
        .map { payment =>
          paymentRepo
            .create(payment.name)
            .map { res =>
              Ok(Json.toJson(res))
            }
        }
        .getOrElse(Future.successful(BadRequest("data error")))
    }

  def createPaymentHandler(): Action[AnyContent] =
    Action async { implicit request =>
      paymentForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.paymentCreate(errorForm))
          )
        },
        payment => {
          paymentRepo
            .create(payment.name)
            .map { _ =>
              Redirect("/api/Payment")
            }
        }
      )
    }

  def update(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val payment = paymentRepo.get(id)
      payment.map(payment => {
        val form = updatePaymentForm.fill(
          UpdatePaymentForm(
            payment.get.id,
            payment.get.name
          )
        )
        Ok(views.html.paymentUpdate(form))
      })
    }

  def updateJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Payment]
        .map { payment =>
          paymentRepo.update(payment.id, payment).map { res =>
            Ok(Json.toJson(res))
          }
        }
        .getOrElse(Future.successful(BadRequest("invalid json")))
    }

  def updatePaymentHandler(): Action[AnyContent] =
    Action.async { implicit request =>
      updatePaymentForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.paymentUpdate(errorForm))
          )
        },
        payment => {
          paymentRepo
            .update(
              payment.id,
              Payment(
                payment.id,
                payment.name
              )
            )
            .map { _ =>
              Redirect("/api/Payment")
            }

        }
      )
    }

  def delete(id: Int) =
    Action {
      paymentRepo.delete(id)
      Redirect("/api/Payment")
    }

  def deleteJSON(id: Int): Action[AnyContent] =
    Action.async {
      paymentRepo.delete(id).map { res =>
        Ok(Json.toJson(res))
      }
    }

}

case class CreatePaymentForm(
    name: String
)
case class UpdatePaymentForm(
    id: Int,
    name: String
)
