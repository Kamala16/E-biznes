package models

import play.api.libs.json.OFormat

case class Favorite(id: Int, userId: Int, productId: Int)

object Favorite {
  implicit val format: OFormat[Favorite] = OFormat[Favorite]
}
