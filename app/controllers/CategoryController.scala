package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class CategoryController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index(): Action[AnyContent] = Action {
    Ok("category list")
  }

  def get(id: Long): Action[AnyContent] = Action {
    Ok(s"category $id")
  }

  def create(): Action[AnyContent] = Action {
    Ok("category created")
  }

  def update(id: Long): Action[AnyContent] = Action {
    Ok(s"category $id updated")
  }

  def delete(id: Long): Action[AnyContent] = Action {
    Ok(s"category $id deleted")
  }


}
