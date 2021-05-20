package models

import play.api.libs.json.{Json, OFormat}

case class Cart(
    id: Int,
    userId: Int,
    productId: Int,
    discountId: Int,
    price: Int
)

object Cart {
  implicit val format: OFormat[Cart] = Json.format[Cart]
}
