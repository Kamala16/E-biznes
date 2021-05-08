package repositories

import models.Rate
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class RateRepo @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]


  import dbConfig._
  import profile.api._

  class RateTable(tag: Tag) extends Table[Rate](tag, _tableName = "Rate") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[Int]("userId")
    def productId = column[Int]("productId")
    def value = column[Int]("value")
    def * = (id, userId, productId, value) <> (Rate.tupled, Rate.unapply)
  }

  val rate = TableQuery[RateTable]

  def create(userId: Int, productId: Int, value: Int): Future[Rate] = db.run {
    (rate.map(c => (c.userId, c.productId, c.value))
      returning rate.map(_.id)
      into ((params, id) => Rate(id, params._1, params._2, params._3))
      ) +=(userId, productId, value)
  }

  def get(id: Int): Future[Option[Rate]] = db.run {
    rate.filter(_.id === id).result.headOption
  }

  def list(): Future[Seq[Rate]] = db.run {
    rate.result
  }

  def update(id: Int, new_carts: Rate): Future[Int] = {
    val updateCart: Rate = new_carts.copy(id)
    db.run(rate.filter(_.id === id).update(updateCart))
  }

  def delete(id: Int): Future[Int] = db.run(rate.filter(_.id === id).delete)
}
