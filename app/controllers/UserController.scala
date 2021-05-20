package controllers

import models.{Cart, User}
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import repositories.{FavoriteRepo, UserRepo}

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject() (
    userRepo: UserRepo,
    favoriteRepo: FavoriteRepo,
    cc: MessagesControllerComponents
)(implicit ec: ExecutionContext)
    extends MessagesAbstractController(cc) {

  val userForm: Form[CreateUserForm] = Form {
    mapping(
      "favoriteId" -> number,
      "name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "passwor" -> nonEmptyText
    )(CreateUserForm.apply)(CreateUserForm.unapply)
  }

  val updateUserForm: Form[UpdateUserForm] = Form {
    mapping(
      "id" -> number,
      "favoriteId" -> number,
      "name" -> nonEmptyText,
      "email" -> nonEmptyText,
      "password" -> nonEmptyText
    )(UpdateUserForm.apply)(UpdateUserForm.unapply)
  }

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      userRepo.list().map { user =>
        Ok(views.html.user(user))
      }
    }

  def indexJSON(): Action[AnyContent] =
    Action.async {
      val user = userRepo.list()
      user.map { user =>
        Ok(Json.toJson(user))
      }
    }

  def create(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val user = userRepo.list()
      user.map(_ => Ok(views.html.userCreate(userForm)))
    }

  def createJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[User]
        .map { user =>
          userRepo
            .create(user.favoriteId, user.name, user.email, user.password)
            .map { res =>
              Ok(Json.toJson(res))
            }
        }
        .getOrElse(Future.successful(BadRequest("data error")))
    }

  def createUserHandler(): Action[AnyContent] =
    Action async { implicit request =>
      userForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.userCreate(errorForm))
          )
        },
        user => {
          userRepo
            .create(user.favoriteId, user.name, user.email, user.password)
            .map { _ =>
              Redirect("/api/User")
            }
        }
      )
    }

  def update(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val user = userRepo.get(id)
      user.map(user => {
        val form = updateUserForm.fill(
          UpdateUserForm(
            user.get.id,
            user.get.favoriteId,
            user.get.name,
            user.get.email,
            user.get.password
          )
        )
        Ok(views.html.userUpdate(form))
      })
    }

  def updateJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[User]
        .map { user =>
          userRepo.update(user.id, user).map { res =>
            Ok(Json.toJson(res))
          }
        }
        .getOrElse(Future.successful(BadRequest("invalid json")))
    }

  def updateUserHandler(): Action[AnyContent] =
    Action.async { implicit request =>
      updateUserForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.userUpdate(errorForm))
          )
        },
        user => {
          userRepo
            .update(
              user.id,
              User(
                user.id,
                user.favoriteId,
                user.name,
                user.email,
                user.password
              )
            )
            .map { _ =>
              Redirect("/api/User")
            }

        }
      )
    }

  def delete(id: Int) =
    Action {
      userRepo.delete(id)
      Redirect("/api/User")
    }

  def deleteJSON(id: Int): Action[AnyContent] =
    Action.async {
      userRepo.delete(id).map { res =>
        Ok(Json.toJson(res))
      }
    }

}

case class CreateUserForm(
    favoriteId: Int,
    name: String,
    email: String,
    password: String
)

case class UpdateUserForm(
    id: Int,
    favoriteId: Int,
    name: String,
    email: String,
    password: String
)
