package models

import play.api.libs.json.{Json, OFormat}

case class Product(
    id: Int,
    categoryId: Int,
    rateId: Int,
    promotionId: Int,
    price: Int
)

object Product {
  implicit val format: OFormat[Product] = Json.format[Product]
}
