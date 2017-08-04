package models

import forms.RegisterForm
import org.joda.time.DateTime
import scalikejdbc._

import scala.util.Try

case class User(
    id: Long,
    name: String,
    password: String,
    email: String,
    createdAt: DateTime) {
  def this() = this(0, "test", "test@gmail.com", "test", new DateTime())
}

object User extends SQLSyntaxSupport[User] {
  override val tableName = "users"
  val u = User.syntax("u")

  GlobalSettings.loggingSQLAndTime = new LoggingSQLAndTimeSettings(
    enabled = true,
    singleLineMode = true,
    logLevel = 'DEBUG
  )

  def apply(e: ResultName[User])(rs: WrappedResultSet): User = new User(
    rs.long(e.id), rs.string(e.name), rs.string(e.password), rs.string(e.email), rs.jodaDateTime(e.createdAt))

  def createUser(registerForm: RegisterForm)(implicit dbsession: DBSession = AutoSession): Try[Int] = Try {
    val u = User.column
    applyUpdate {
      insert
        .into(User)
        .columns(u.name, u.password, u.email, u.createdAt)
        .values(registerForm.username, registerForm.password, registerForm.email, new DateTime())
    }
  }

  def getUserByEmail(email: String)(implicit dbsession: DBSession = AutoSession): Try[Option[User]] = Try {
    withSQL { select.from(User as u).where.eq(u.email, email) }.map(User(u.resultName)).single.apply()
  }

  def getUserByEmailAndPass(email: String, password: String)(implicit dbsession: DBSession = AutoSession): Try[Option[User]] = Try {
    withSQL { select.from(User as u).where.eq(u.email, email).and.eq(u.password, password) }.map(User(u.resultName)).single.apply()
  }

  def getUserById(id: Long)(implicit dbsession: DBSession = AutoSession): Try[Option[User]] = Try {
    withSQL { select.from(User as u).where.eq(u.id, id) }.map(User(u.resultName)).single.apply()
  }

}
