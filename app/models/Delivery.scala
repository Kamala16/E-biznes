package models

import play.api.libs.json.OFormat

case class Delivery(id: Int, name: String)

object Delivery {
  implicit val format: OFormat[Delivery] = OFormat[Delivery]
}
