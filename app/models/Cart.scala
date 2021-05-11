package models

import play.api.libs.json.OFormat

case class Cart(
    id: Int,
    userId: Int,
    productId: Int,
    discountId: Int,
    price: Int
)

object Cart {
  implicit val format: OFormat[Cart] = OFormat[Cart]
}
