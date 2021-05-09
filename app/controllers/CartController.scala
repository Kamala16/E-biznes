package controllers

import play.api.mvc._
import repositories.CartRepo

import javax.inject._

@Singleton
class CartController @Inject()(cartRepo: CartRepo, cc: ControllerComponents) extends AbstractController(cc) {
  def index(): Action[AnyContent] = Action {
    Ok("cart list")
  }

  def get(id: Long): Action[AnyContent] = Action {
    Ok(s"cart $id")
  }

  def create(): Action[AnyContent] = Action {
    Ok("cart created")
  }

  def update(id: Long): Action[AnyContent] = Action {
    Ok(s"cart $id updated")
  }

  def delete(id: Long): Action[AnyContent] = Action {
    Ok(s"cart $id deleted")
  }


}
