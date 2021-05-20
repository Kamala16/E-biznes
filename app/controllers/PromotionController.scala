package controllers

import models.{Promotion}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import repositories.{ProductRepo, PromotionRepo}

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PromotionController @Inject() (
    promotionRepo: PromotionRepo,
    productRepo: ProductRepo,
    cc: MessagesControllerComponents
)(implicit ec: ExecutionContext)
    extends MessagesAbstractController(cc) {

  val promotionForm: Form[CreatePromotionForm] = Form {
    mapping(
      "productId" -> number,
      "value" -> number
    )(CreatePromotionForm.apply)(CreatePromotionForm.unapply)
  }

  val updatePromotionForm: Form[UpdatePromotionForm] = Form {
    mapping(
      "id" -> number,
      "productId" -> number,
      "value" -> number
    )(UpdatePromotionForm.apply)(UpdatePromotionForm.unapply)
  }

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      promotionRepo.list().map { promotion =>
        Ok(views.html.promotion(promotion))
      }
    }

  def indexJSON(): Action[AnyContent] =
    Action.async {
      val promotion = promotionRepo.list()
      promotion.map { promotion =>
        Ok(Json.toJson(promotion))
      }
    }

  def create(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val promotion = promotionRepo.list()
      promotion.map(_ => Ok(views.html.promotionCreate(promotionForm)))
    }

  def createJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Promotion]
        .map { promotion =>
          promotionRepo
            .create(promotion.productId, promotion.value)
            .map { res =>
              Ok(Json.toJson(res))
            }
        }
        .getOrElse(Future.successful(BadRequest("data error")))
    }

  def createPromotionHandler(): Action[AnyContent] =
    Action async { implicit request =>
      promotionForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.promotionCreate(errorForm))
          )
        },
        promotion => {
          promotionRepo
            .create(promotion.productId, promotion.value)
            .map { _ =>
              Redirect("/api/Promotion")
            }
        }
      )
    }

  def update(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val promotion = promotionRepo.get(id)
      promotion.map(promotion => {
        val form = updatePromotionForm.fill(
          UpdatePromotionForm(
            promotion.get.id,
            promotion.get.productId,
            promotion.get.value
          )
        )
        Ok(views.html.promotionUpdate(form))
      })
    }

  def updateJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Promotion]
        .map { promotion =>
          promotionRepo.update(promotion.id, promotion).map { res =>
            Ok(Json.toJson(res))
          }
        }
        .getOrElse(Future.successful(BadRequest("invalid json")))
    }

  def updatePromotionHandler(): Action[AnyContent] =
    Action.async { implicit request =>
      updatePromotionForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.promotionUpdate(errorForm))
          )
        },
        promotion => {
          promotionRepo
            .update(
              promotion.id,
              Promotion(
                promotion.id,
                promotion.productId,
                promotion.value
              )
            )
            .map { _ =>
              Redirect("/api/Promotion")
            }

        }
      )
    }

  def delete(id: Int) =
    Action {
      promotionRepo.delete(id)
      Redirect("/api/Promotion")
    }

  def deleteJSON(id: Int): Action[AnyContent] =
    Action.async {
      promotionRepo.delete(id).map { res =>
        Ok(Json.toJson(res))
      }
    }

}

case class CreatePromotionForm(
    productId: Int,
    value: Int
)

case class UpdatePromotionForm(
    id: Int,
    productId: Int,
    value: Int
)
