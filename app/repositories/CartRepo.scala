package repositories

import models.Cart
import play.api.db.slick.DatabaseConfigProvider

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.JdbcProfile

@Singleton
class CartRepo @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]


  import dbConfig._
  import profile.api._

  class CartTable(tag: Tag) extends Table[Cart](tag, _tableName = "Cart") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[Int]("userId")
    def productId = column[Int]("productId")
    def discountId = column[Int]("discountId")
    def price = column[Double]("price")
    def * = (id, userId, productId, discountId, price) <> (Cart.tupled, Cart.unapply)
  }

  val carts = TableQuery[CartTable]

  def create(userId: Int, productId: Int, discountId: Int, price: Double): Future[Cart] = db.run {
    (carts.map(c => (c.userId, c.productId, c.discountId, c.price))
      returning carts.map(_.id)
      into ((params, id) => Cart(id, params._1, params._2, params._3, params._4))
      ) +=(userId, productId, discountId, price)
  }

  def get(id: Int): Future[Option[Cart]] = db.run {
    carts.filter(_.id === id).result.headOption
  }

  def list(): Future[Seq[Cart]] = db.run {
    carts.result
  }

  def update(id: Int, new_carts: Cart): Future[Int] = {
    val updateCart: Cart = new_carts.copy(id)
    db.run(carts.filter(_.id === id).update(updateCart))
  }

  def delete(id: Int): Future[Int] = db.run(carts.filter(_.id === id).delete)
}
