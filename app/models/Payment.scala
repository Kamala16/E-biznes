package models

import play.api.libs.json.{Json, OFormat}

case class Payment(id: Int, name: String)

object Payment {
  implicit val format: OFormat[Payment] = Json.format[Payment]
}
