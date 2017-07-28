package services

import forms.{ LoginForm, RegisterForm }
import models.{ User }

import scala.util.{ Failure, Success, Try }

trait UserService {
  def createNewUser(registerForm: RegisterForm): ResultStatus.Value

  def getUserByEmail(email: String): Option[User]

  def checkLogin(loginForm: LoginForm): Option[User]

}

class UserServiceImpl extends UserService {
  override def createNewUser(registerForm: RegisterForm): ResultStatus.Value = {
    val userExist = getUserByEmail(registerForm.email)
    userExist match {
      case None =>
        val user: Try[Int] = User.createUser(registerForm)
        user match {
          case Success(user) => ResultStatus.Succeed
          case Failure(exception) =>
            println("Error: " + exception.getMessage)
            ResultStatus.Excepted
        }

      case _ =>
        ResultStatus.Failed
    }
  }

  override def getUserByEmail(email: String): Option[User] = {
    val user: Try[Option[User]] = User.getUserByEmail(email)
    user match {
      case Success(user) => user
      case Failure(exception) =>
        println("Error: " + exception.getMessage)
        None
    }
  }

  override def checkLogin(loginForm: LoginForm): Option[User] = {
    val user: Try[Option[User]] = User.getUserByEmailAndPass(loginForm.email, loginForm.password)
    user match {
      case Success(user) =>
        user match {
          case None => Some(new User())
          case _    => user
        }
      case Failure(exception) => None
    }
  }
}
