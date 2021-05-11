package models

import play.api.libs.json.OFormat

case class Rate(id: Int, userId: Int, productId: Int, value: Int)

object Rate {
  implicit val format: OFormat[Rate] = OFormat[Rate]
}
