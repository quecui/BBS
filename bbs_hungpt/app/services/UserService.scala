package services

import forms.{ LoginForm, RegisterForm }
import models.{ User }

import scala.util.{ Failure, Success, Try }

trait UserService {
  def createNewUser(registerForm: RegisterForm): ResultStatus.Value

  def getUserByEmail(email: String): Try[User]

  def checkLogin(loginForm: LoginForm): Try[User]

}

class UserServiceImpl extends UserService {
  override def createNewUser(registerForm: RegisterForm): ResultStatus.Value = {
    val userExist = getUserByEmail(registerForm.email)
    userExist match {
      case Failure(userExist) =>
        User.createUser(registerForm) match {
          case Success(user) =>
            ResultStatus.Succeed
          case Failure(exception) =>
            ResultStatus.Excepted
        }

      case _ => ResultStatus.Failed
    }
  }

  override def getUserByEmail(email: String): Try[User] = Try {
    User.getUserByEmail(email).map(user => user.get).getOrElse(throw new Exception("User not Found"))
  }

  override def checkLogin(loginForm: LoginForm): Try[User] = Try {
    User.getUserByEmailAndPass(loginForm.email, loginForm.password).map(user => user.get).getOrElse(throw new Exception("Login Fail"))
  }
}
