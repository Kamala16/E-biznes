package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class PaymentController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index(): Action[AnyContent] = Action {
    Ok("payment list")
  }

  def get(id: Long): Action[AnyContent] = Action {
    Ok(s"payment $id")
  }

  def create(): Action[AnyContent] = Action {
    Ok("payment created")
  }

  def update(id: Long): Action[AnyContent] = Action {
    Ok(s"payment $id updated")
  }

  def delete(id: Long): Action[AnyContent] = Action {
    Ok(s"payment $id deleted")
  }


}
