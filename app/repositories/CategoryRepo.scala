package repositories

import models.Category
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CategoryRepo @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]


  import dbConfig._
  import profile.api._

  class CategoryTable(tag: Tag) extends Table[Category](tag, _tableName = "Category") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def productId = column[Int]("productId")
    def name = column[String]("name")
    def * = (id, productId, name) <> (Category.tupled, Category.unapply)
  }

  val category = TableQuery[CategoryTable]

  def create(productId: Int, name: String): Future[Category] = db.run {
    (category.map(c => (c.productId, c.name))
      returning category.map(_.id)
      into ((params, id) => Category(id, params._1, params._2))
      ) +=(productId, name)
  }

  def get(id: Int): Future[Option[Category]] = db.run {
    category.filter(_.id === id).result.headOption
  }

  def list(): Future[Seq[Category]] = db.run {
    category.result
  }

  def update(id: Int, new_carts: Category): Future[Int] = {
    val updateCart: Category = new_carts.copy(id)
    db.run(category.filter(_.id === id).update(updateCart))
  }

  def delete(id: Int): Future[Int] = db.run(category.filter(_.id === id).delete)
}
