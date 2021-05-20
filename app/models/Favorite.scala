package models

import play.api.libs.json.{Json, OFormat}

case class Favorite(id: Int, userId: Int, productId: Int)

object Favorite {
  implicit val format: OFormat[Favorite] = Json.format[Favorite]
}
