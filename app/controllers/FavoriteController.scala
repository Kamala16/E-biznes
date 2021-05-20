package controllers

import models.{Favorite}
import play.api.data.Form
import play.api.data.Forms.{mapping, number}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import repositories.{FavoriteRepo, ProductRepo, UserRepo}

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FavoriteController @Inject() (
    favoriteRepo: FavoriteRepo,
    userRepo: UserRepo,
    productRepo: ProductRepo,
    cc: MessagesControllerComponents
)(implicit
    ec: ExecutionContext
) extends MessagesAbstractController(cc) {

  val favoriteForm: Form[CreateFavoriteForm] = Form {
    mapping(
      "userId" -> number,
      "productId" -> number
    )(CreateFavoriteForm.apply)(CreateFavoriteForm.unapply)
  }

  val updateFavoriteForm: Form[UpdateFavoriteForm] = Form {
    mapping(
      "id" -> number,
      "userId" -> number,
      "productId" -> number
    )(UpdateFavoriteForm.apply)(UpdateFavoriteForm.unapply)
  }

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      favoriteRepo.list().map { favorite =>
        Ok(views.html.favorite(favorite))
      }
    }

  def indexJSON(): Action[AnyContent] =
    Action.async {
      val favorite = favoriteRepo.list()
      favorite.map { favorite =>
        Ok(Json.toJson(favorite))
      }
    }

  def create(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val favorite = favoriteRepo.list()
      favorite.map(_ => Ok(views.html.favoriteCreate(favoriteForm)))
    }

  def createJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Favorite]
        .map { favorite =>
          favoriteRepo
            .create(favorite.userId, favorite.productId)
            .map { res =>
              Ok(Json.toJson(res))
            }
        }
        .getOrElse(Future.successful(BadRequest("data error")))
    }

  def createFavoriteHandler(): Action[AnyContent] =
    Action async { implicit request =>
      favoriteForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.favoriteCreate(errorForm))
          )
        },
        favorite => {
          favoriteRepo
            .create(favorite.userId, favorite.productId)
            .map { _ =>
              Redirect("/api/Favorite")
            }
        }
      )
    }

  def update(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val favorite = favoriteRepo.get(id)
      favorite.map(favorite => {
        val form = updateFavoriteForm.fill(
          UpdateFavoriteForm(
            favorite.get.id,
            favorite.get.userId,
            favorite.get.productId
          )
        )
        Ok(views.html.favoriteUpdate(form))
      })
    }

  def updateJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Favorite]
        .map { favorite =>
          favoriteRepo.update(favorite.id, favorite).map { res =>
            Ok(Json.toJson(res))
          }
        }
        .getOrElse(Future.successful(BadRequest("invalid json")))
    }

  def updateFavoriteHandler(): Action[AnyContent] =
    Action.async { implicit request =>
      updateFavoriteForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.favoriteUpdate(errorForm))
          )
        },
        favorite => {
          favoriteRepo
            .update(
              favorite.id,
              Favorite(
                favorite.id,
                favorite.userId,
                favorite.productId
              )
            )
            .map { _ =>
              Redirect("/api/Favorite")
            }

        }
      )
    }

  def delete(id: Int) =
    Action {
      favoriteRepo.delete(id)
      Redirect("/api/Favorite")
    }

  def deleteJSON(id: Int): Action[AnyContent] =
    Action.async {
      favoriteRepo.delete(id).map { res =>
        Ok(Json.toJson(res))
      }
    }

}

case class CreateFavoriteForm(
    userId: Int,
    productId: Int
)
case class UpdateFavoriteForm(
    id: Int,
    userId: Int,
    productId: Int
)
