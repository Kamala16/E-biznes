package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class FavoriteController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index(): Action[AnyContent] = Action {
    Ok("favorite list")
  }

  def get(id: Long): Action[AnyContent] = Action {
    Ok(s"favorite $id")
  }

  def create(): Action[AnyContent] = Action {
    Ok("favorite created")
  }

  def update(id: Long): Action[AnyContent] = Action {
    Ok(s"favorite $id updated")
  }

  def delete(id: Long): Action[AnyContent] = Action {
    Ok(s"favorite $id deleted")
  }


}
