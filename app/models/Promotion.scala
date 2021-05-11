package models

import play.api.libs.json.OFormat

case class Promotion(id: Int, productId: Int, value: Int)

object Promotion {
  implicit val format: OFormat[Promotion] = OFormat[Promotion]
}
