package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class DeliveryController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index(): Action[AnyContent] = Action {
    Ok("delivery list")
  }

  def get(id: Long): Action[AnyContent] = Action {
    Ok(s"delivery $id")
  }

  def create(): Action[AnyContent] = Action {
    Ok("delivery created")
  }

  def update(id: Long): Action[AnyContent] = Action {
    Ok(s"delivery $id updated")
  }

  def delete(id: Long): Action[AnyContent] = Action {
    Ok(s"delivery $id deleted")
  }


}
