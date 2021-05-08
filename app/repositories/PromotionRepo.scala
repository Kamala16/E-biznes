package repositories

import models.Promotion
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PromotionRepo @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]


  import dbConfig._
  import profile.api._

  class PromotionTable(tag: Tag) extends Table[Promotion](tag, _tableName = "Promotion") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def productId = column[Int]("productId")
    def value = column[Int]("value")
    def * = (id, productId, value) <> (Promotion.tupled, Promotion.unapply)
  }

  val promotion = TableQuery[PromotionTable]

  def create(productId: Int, value: Int): Future[Promotion] = db.run {
    (promotion.map(c => (c.productId, c.value))
      returning promotion.map(_.id)
      into ((params, id) => Promotion(id, params._1, params._2))
      ) +=(productId, value)
  }

  def get(id: Int): Future[Option[Promotion]] = db.run {
    promotion.filter(_.id === id).result.headOption
  }

  def list(): Future[Seq[Promotion]] = db.run {
    promotion.result
  }

  def update(id: Int, new_carts: Promotion): Future[Int] = {
    val updateCart: Promotion = new_carts.copy(id)
    db.run(promotion.filter(_.id === id).update(updateCart))
  }

  def delete(id: Int): Future[Int] = db.run(promotion.filter(_.id === id).delete)
}
