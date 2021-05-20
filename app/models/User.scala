package models

import play.api.libs.json.{Json, OFormat}

case class User(
    id: Int,
    favoriteId: Int,
    name: String,
    email: String,
    password: String
)

object User {
  implicit val format: OFormat[User] = Json.format[User]
}
