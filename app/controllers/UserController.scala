package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index(): Action[AnyContent] = Action {
    Ok("user list")
  }

  def get(id: Long): Action[AnyContent] = Action {
    Ok(s"user $id")
  }

  def create(): Action[AnyContent] = Action {
    Ok("user created")
  }

  def update(id: Long): Action[AnyContent] = Action {
    Ok(s"user $id updated")
  }

  def delete(id: Long): Action[AnyContent] = Action {
    Ok(s"user $id deleted")
  }


}
