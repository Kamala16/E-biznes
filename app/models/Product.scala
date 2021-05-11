package models

import play.api.libs.json.OFormat

case class Product(
    id: Int,
    categoryId: Int,
    rateId: Int,
    promotionId: Int,
    price: Double
)

object Product {
  implicit val format: OFormat[Product] = OFormat[Product]
}
