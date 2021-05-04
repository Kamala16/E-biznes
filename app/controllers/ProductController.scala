package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class ProductController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index(): Action[AnyContent] = Action {
    Ok("product list")
  }

  def get(id: Long): Action[AnyContent] = Action {
    Ok(s"product $id")
  }

  def create(): Action[AnyContent] = Action {
    Ok("product created")
  }

  def update(id: Long): Action[AnyContent] = Action {
    Ok(s"product $id updated")
  }

  def delete(id: Long): Action[AnyContent] = Action {
    Ok(s"product $id deleted")
  }


}
