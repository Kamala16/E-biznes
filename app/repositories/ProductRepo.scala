package repositories

import models.Product
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductRepo @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]


  import dbConfig._
  import profile.api._

  class ProductTable(tag: Tag) extends Table[Product](tag, _tableName = "Product") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def categoryId = column[Int]("categoryId")
    def rateId = column[Int]("rateId")
    def promotionId = column[Int]("promotionId")
    def price = column[Double]("price")
    def * = (id, categoryId, rateId, promotionId, price) <> (Product.tupled, Product.unapply)
  }

  val product = TableQuery[ProductTable]

  def create(categoryId: Int, rateId: Int, promotionId: Int, price: Double): Future[Product] = db.run {
    (product.map(c => (c.categoryId, c.rateId, c.promotionId, c.price))
      returning product.map(_.id)
      into ((params, id) => Product(id, params._1, params._2, params._3, params._4))
      ) +=(categoryId, rateId, promotionId, price)
  }

  def get(id: Int): Future[Option[Product]] = db.run {
    product.filter(_.id === id).result.headOption
  }

  def list(): Future[Seq[Product]] = db.run {
    product.result
  }

  def update(id: Int, new_carts: Product): Future[Int] = {
    val updateCart: Product = new_carts.copy(id)
    db.run(product.filter(_.id === id).update(updateCart))
  }

  def delete(id: Int): Future[Int] = db.run(product.filter(_.id === id).delete)
}
