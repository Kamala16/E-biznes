package repositories

import models.Discount
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DiscountRepo @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]


  import dbConfig._
  import profile.api._

  class DiscountTable(tag: Tag) extends Table[Discount](tag, _tableName = "Discount") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def value = column[Int]("value")
    def * = (id, value) <> (Discount.tupled, Discount.unapply)
  }

  val discount = TableQuery[DiscountTable]

  def create(userId: Int, value: Int): Future[Discount] = db.run {
    (discount.map(c => (c.value))
      returning discount.map(_.id)
      into ((value, id) => Discount(id, value))
      ) +=(value)
  }

  def get(id: Int): Future[Option[Discount]] = db.run {
    discount.filter(_.id === id).result.headOption
  }

  def list(): Future[Seq[Discount]] = db.run {
    discount.result
  }

  def update(id: Int, new_carts: Discount): Future[Int] = {
    val updateCart: Discount = new_carts.copy(id)
    db.run(discount.filter(_.id === id).update(updateCart))
  }

  def delete(id: Int): Future[Int] = db.run(discount.filter(_.id === id).delete)
}
