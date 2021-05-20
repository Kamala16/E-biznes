package controllers

import models.{Cart, Rate}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import repositories.{ProductRepo, RateRepo, UserRepo}

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RateController @Inject() (
    rateRepo: RateRepo,
    userRepo: UserRepo,
    productRepo: ProductRepo,
    cc: MessagesControllerComponents
)(implicit
    ec: ExecutionContext
) extends MessagesAbstractController(cc) {

  val rateForm: Form[CreateRateForm] = Form {
    mapping(
      "userId" -> number,
      "productId" -> number,
      "value" -> number
    )(CreateRateForm.apply)(CreateRateForm.unapply)
  }

  val updateRateForm: Form[UpdateRateForm] = Form {
    mapping(
      "id" -> number,
      "userId" -> number,
      "productId" -> number,
      "value" -> number
    )(UpdateRateForm.apply)(UpdateRateForm.unapply)
  }

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      rateRepo.list().map { rate =>
        Ok(views.html.rate(rate))
      }
    }

  def indexJSON(): Action[AnyContent] =
    Action.async {
      val rate = rateRepo.list()
      rate.map { rate =>
        Ok(Json.toJson(rate))
      }
    }

  def create(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val rate = rateRepo.list()
      rate.map(_ => Ok(views.html.rateCreate(rateForm)))
    }

  def createJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Rate]
        .map { rate =>
          rateRepo
            .create(rate.userId, rate.productId, rate.value)
            .map { res =>
              Ok(Json.toJson(res))
            }
        }
        .getOrElse(Future.successful(BadRequest("data error")))
    }

  def createRateHandler(): Action[AnyContent] =
    Action async { implicit request =>
      rateForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.rateCreate(errorForm))
          )
        },
        rate => {
          rateRepo
            .create(rate.userId, rate.productId, rate.value)
            .map { _ =>
              Redirect("/api/Rate")
            }
        }
      )
    }

  def update(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val rate = rateRepo.get(id)
      rate.map(rate => {
        val form = updateRateForm.fill(
          UpdateRateForm(
            rate.get.id,
            rate.get.userId,
            rate.get.productId,
            rate.get.value
          )
        )
        Ok(views.html.rateUpdate(form))
      })
    }

  def updateJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Rate]
        .map { rate =>
          rateRepo.update(rate.id, rate).map { res =>
            Ok(Json.toJson(res))
          }
        }
        .getOrElse(Future.successful(BadRequest("invalid json")))
    }

  def updateRateHandler(): Action[AnyContent] =
    Action.async { implicit request =>
      updateRateForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.rateUpdate(errorForm))
          )
        },
        rate => {
          rateRepo
            .update(
              rate.id,
              Rate(
                rate.id,
                rate.userId,
                rate.productId,
                rate.value
              )
            )
            .map { _ =>
              Redirect("/api/Rate")
            }

        }
      )
    }

  def delete(id: Int) =
    Action {
      rateRepo.delete(id)
      Redirect("/api/Rate")
    }

  def deleteJSON(id: Int): Action[AnyContent] =
    Action.async {
      rateRepo.delete(id).map { res =>
        Ok(Json.toJson(res))
      }
    }

}

case class CreateRateForm(
    userId: Int,
    productId: Int,
    value: Int
)

case class UpdateRateForm(
    id: Int,
    userId: Int,
    productId: Int,
    value: Int
)
