package models

import play.api.libs.json.OFormat

case class Discount(id: Int, value: Int)

object Discount {
  implicit val format: OFormat[Discount] = OFormat[Discount]
}
