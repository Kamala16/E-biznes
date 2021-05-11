package models

import play.api.libs.json.OFormat

case class Payment(id: Int, name: String)

object Payment {
  implicit val format: OFormat[Payment] = OFormat[Payment]
}
