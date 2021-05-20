package models

import play.api.libs.json.{Json, OFormat}

case class Discount(id: Int, userId: Int, value: Int)

object Discount {
  implicit val format: OFormat[Discount] = Json.format[Discount]
}
