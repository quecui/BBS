package models

import org.joda.time.DateTime
import scalikejdbc._
/**
  * Created by hung_pt on 7/20/17.
  */

case class User(
               id: Long,
               name: String,
               email: String,
               createdAt: DateTime
               )

object User extends SQLSyntaxSupport[User] {
  override val tableName = "users"

  //stringOpt # string ????
  def apply(rs: WrappedResultSet) = new User(
    rs.long("id"), rs.string("name"), rs.string("email"), rs.jodaDateTime("created_at"))
}
