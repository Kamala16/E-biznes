package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class DiscountController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index(): Action[AnyContent] = Action {
    Ok("discount list")
  }

  def get(id: Long): Action[AnyContent] = Action {
    Ok(s"discount $id")
  }

  def create(): Action[AnyContent] = Action {
    Ok("discount created")
  }

  def update(id: Long): Action[AnyContent] = Action {
    Ok(s"discount $id updated")
  }

  def delete(id: Long): Action[AnyContent] = Action {
    Ok(s"discount $id deleted")
  }


}
