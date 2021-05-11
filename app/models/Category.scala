package models

import play.api.libs.json.OFormat

case class Category(id: Int, productId: Int, name: String)

object Category {
  implicit val format: OFormat[Category] = OFormat[Category]
}
