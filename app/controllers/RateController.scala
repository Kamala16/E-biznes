package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class RateController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def index(): Action[AnyContent] = Action {
    Ok("rate list")
  }

  def get(id: Long): Action[AnyContent] = Action {
    Ok(s"rate $id")
  }

  def create(): Action[AnyContent] = Action {
    Ok("rate created")
  }

  def update(id: Long): Action[AnyContent] = Action {
    Ok(s"rate $id updated")
  }

  def delete(id: Long): Action[AnyContent] = Action {
    Ok(s"rate $id deleted")
  }


}
