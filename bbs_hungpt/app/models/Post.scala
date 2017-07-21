package models

import contants.ConstantData
import org.joda.time.DateTime
import scalikejdbc.WrappedResultSet
import scalikejdbc._
/**
  * Created by hung_pt on 7/19/17.
  */

case class Post(
                 id: Long,
                 title: Option[String],
                 content: Option[String],
                 createdAt: DateTime,
                 authorId: Long
               )


object Post extends SQLSyntaxSupport[Post] {
  override val tableName = "posts"

  def apply(rs: WrappedResultSet) = new Post(
    rs.long("id"), rs.stringOpt("title"), rs.stringOpt("content"), rs.jodaDateTime("created_at"), rs.long("author_id"))

  def findAll()(implicit session: DBSession = autoSession): List[Post] =
    sql"select * from posts".map(rs => Post(rs)).list.apply()

  def defaultData()(implicit session: DBSession = autoSession): Unit = {
    sql"insert into posts (title, content, created_at, author_id) values (${ConstantData.title}, ${ConstantData.content}, ${ConstantData.createAt}, 1)".update.apply()
  }
}

