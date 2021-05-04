package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class PromotionController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index(): Action[AnyContent] = Action {
    Ok("promotion list")
  }

  def get(id: Long): Action[AnyContent] = Action {
    Ok(s"promotion $id")
  }

  def create(): Action[AnyContent] = Action {
    Ok("promotion created")
  }

  def update(id: Long): Action[AnyContent] = Action {
    Ok(s"promotion $id updated")
  }

  def delete(id: Long): Action[AnyContent] = Action {
    Ok(s"promotion $id deleted")
  }


}
