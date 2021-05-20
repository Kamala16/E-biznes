package models

import play.api.libs.json.{Json, OFormat}

case class Delivery(id: Int, userId: Int, name: String)

object Delivery {
  implicit val format: OFormat[Delivery] = Json.format[Delivery]
}
