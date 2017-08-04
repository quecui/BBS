package services

import forms.{ LoginForm, RegisterForm }
import models.User
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import play.api.test.WithApplication
import scalikejdbc.config.DBs

import scala.util.{ Failure, Success }

class UserServiceSpec extends Specification with Mockito {
  val userService = new UserServiceImpl
  DBs.setupAll()

  val form = RegisterForm("test", "test@gmail.com", "test")

  "User Service" should {
    "Create New User#no duplicate" in new WithApplication() {
      val result = userService.createNewUser(form)
      assert(result == ResultStatus.Succeed)
    }

    "Create New User # duplicate" in new WithApplication() {
      val result = userService.createNewUser(form)
      assert(result == ResultStatus.Failed)
    }

    "GetUserByEmail" in new WithApplication() {
      val user = userService.getUserByEmail("hung_pt@septeni-technology.jp")
      assert(user == Success())
    }

    "CheckLogin # success" in new WithApplication() {
      val user = userService.checkLogin(LoginForm("hung_pt@septeni-technology.jp", "hunghp1502"))
      assert(user == Success())
    }

    "CheckLogin # failure" in new WithApplication() {
      val user = userService.checkLogin(LoginForm("hung_pt22@septeni-technology.jp", "hunghp1502"))
      assert(user == Failure(new Exception("Login Fail")))
    }
  }

}
