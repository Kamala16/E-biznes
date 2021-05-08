package repositories

import models.User
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepo @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]


  import dbConfig._
  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, _tableName = "User") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def favoriteId = column[Int]("favorite")
    def name = column[String]("name")
    def email = column[String]("email")
    def password = column[String]("password")
    def * = (id, favoriteId, name, email, password) <> (User.tupled, User.unapply)
  }

  val user = TableQuery[UserTable]

  def create(favoriteId: Int, name: String, email: String, password: String): Future[User] = db.run {
    (user.map(c => (c.favoriteId, c.name, c.email, c.password))
      returning user.map(_.id)
      into ((params, id) => User(id, params._1, params._2, params._3, params._4))
      ) +=(favoriteId, name, email, password)
  }

  def get(id: Int): Future[Option[User]] = db.run {
    user.filter(_.id === id).result.headOption
  }

  def list(): Future[Seq[User]] = db.run {
    user.result
  }

  def update(id: Int, new_carts: User): Future[Int] = {
    val updateCart: User = new_carts.copy(id)
    db.run(user.filter(_.id === id).update(updateCart))
  }

  def delete(id: Int): Future[Int] = db.run(user.filter(_.id === id).delete)
}
