package repositories

import models.Favorite
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FavoriteRepo @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit
    ec: ExecutionContext
) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class FavoriteTable(tag: Tag)
      extends Table[Favorite](tag, _tableName = "favorite") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[Int]("userId")
    def productId = column[Int]("productId")
    def * =
      (id, userId, productId) <> ((Favorite.apply _).tupled, Favorite.unapply)
  }

  val favorite = TableQuery[FavoriteTable]

  def create(
      userId: Int,
      productId: Int
  ): Future[Favorite] =
    db.run {
      (favorite.map(c => (c.userId, c.productId))
        returning favorite.map(_.id)
        into ((params, id) =>
          Favorite(id, params._1, params._2)
        )) += (userId, productId)
    }

  def get(id: Int): Future[Option[Favorite]] =
    db.run {
      favorite.filter(_.id === id).result.headOption
    }

  def list(): Future[Seq[Favorite]] =
    db.run {
      favorite.result
    }

  def update(id: Int, new_favorite: Favorite): Future[Int] = {
    val updateFavorite: Favorite = new_favorite.copy(id)
    db.run(favorite.filter(_.id === id).update(updateFavorite))
  }

  def delete(id: Int): Future[Int] = db.run(favorite.filter(_.id === id).delete)
}
