package controllers

import models.Category
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import repositories.{CategoryRepo, ProductRepo}

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CategoryController @Inject() (
    categoryRepo: CategoryRepo,
    productRepo: ProductRepo,
    cc: MessagesControllerComponents
)(implicit
    ec: ExecutionContext
) extends MessagesAbstractController(cc) {

  val categoryForm: Form[CreateCategoryForm] = Form {
    mapping(
      "productId" -> number,
      "name" -> nonEmptyText
    )(CreateCategoryForm.apply)(CreateCategoryForm.unapply)
  }

  val updateCategoryForm: Form[UpdateCategoryForm] = Form {
    mapping(
      "id" -> number,
      "productId" -> number,
      "name" -> nonEmptyText
    )(UpdateCategoryForm.apply)(UpdateCategoryForm.unapply)
  }

  def index(): Action[AnyContent] =
    Action.async { implicit request =>
      categoryRepo.list().map { category =>
        Ok(views.html.category(category))
      }
    }

  def indexJSON(): Action[AnyContent] =
    Action.async {
      val category = categoryRepo.list()
      category.map { category =>
        Ok(Json.toJson(category))
      }
    }

  def create(): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val category = categoryRepo.list()
      category.map(_ => Ok(views.html.categoryCreate(categoryForm)))
    }

  def createJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Category]
        .map { category =>
          categoryRepo
            .create(category.productId, category.name)
            .map { res =>
              Ok(Json.toJson(res))
            }
        }
        .getOrElse(Future.successful(BadRequest("data error")))
    }

  def createCategoryHandler(): Action[AnyContent] =
    Action async { implicit request =>
      categoryForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.categoryCreate(errorForm))
          )
        },
        category => {
          categoryRepo
            .create(category.productId, category.name)
            .map { _ =>
              Redirect("/api/Category")
            }
        }
      )
    }

  def update(id: Int): Action[AnyContent] =
    Action.async { implicit request: MessagesRequest[AnyContent] =>
      val category = categoryRepo.get(id)
      category.map(category => {
        val form = updateCategoryForm.fill(
          UpdateCategoryForm(
            category.get.id,
            category.get.productId,
            category.get.name
          )
        )
        Ok(views.html.categoryUpdate(form))
      })
    }

  def updateJSON(): Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body
        .validate[Category]
        .map { category =>
          categoryRepo.update(category.id, category).map { res =>
            Ok(Json.toJson(res))
          }
        }
        .getOrElse(Future.successful(BadRequest("invalid json")))
    }

  def updateCategoryHandler(): Action[AnyContent] =
    Action.async { implicit request =>
      updateCategoryForm.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.categoryUpdate(errorForm))
          )
        },
        category => {
          categoryRepo
            .update(
              category.id,
              Category(
                category.id,
                category.productId,
                category.name
              )
            )
            .map { _ =>
              Redirect("/api/Category")
            }

        }
      )
    }

  def delete(id: Int) =
    Action {
      categoryRepo.delete(id)
      Redirect("/api/Category")
    }

  def deleteJSON(id: Int): Action[AnyContent] =
    Action.async {
      categoryRepo.delete(id).map { res =>
        Ok(Json.toJson(res))
      }
    }

}

case class CreateCategoryForm(
    productId: Int,
    name: String
)
case class UpdateCategoryForm(
    id: Int,
    productId: Int,
    name: String
)
