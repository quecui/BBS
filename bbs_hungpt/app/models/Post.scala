package models

import org.joda.time.DateTime
import scalikejdbc.{ WrappedResultSet, insert, _ }

import scala.util.Try
/**
 * Created by hung_pt on 7/19/17.
 */

case class Post(
    id: Long,
    title: String,
    content: String,
    createdAt: DateTime,
    authorId: Long)

object Post extends SQLSyntaxSupport[Post] {
  override val tableName = "posts"
  val p = Post.syntax("p")

  def apply(e: ResultName[Post])(rs: WrappedResultSet): Post = new Post(
    rs.long(e.id), rs.string(e.title), rs.string(e.content), rs.jodaDateTime(e.createdAt), rs.long(e.authorId))

  def findAll()(implicit session: DBSession = autoSession): Try[List[Post]] =
    Try {
      withSQL { select.from(Post as p) }.map(Post(p.resultName)).list().apply()
    }

  def createPost(postForm: PostForm)(implicit dbsession: DBSession = AutoSession): Try[Int] = Try {
    val p = Post.column
    applyUpdate {
      insert
        .into(Post)
        .columns(p.title, p.content, p.createdAt, p.authorId)
        .values(postForm.title, postForm.content, new DateTime(), 1)
    }
  }

}

