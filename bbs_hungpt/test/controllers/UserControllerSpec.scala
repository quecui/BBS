package controllers

import forms.{ LoginForm, RegisterForm }
import models.User
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import play.api.test.Helpers._
import play.api.test.{ FakeRequest, WithApplication }
import scalikejdbc.config.DBs
import services.{ ResultStatus, UserService }
import play.api.test.CSRFTokenHelper._
import scala.util.{ Failure, Success }

class UserControllerSpec extends Specification with Mockito {
  DBs.setupAll()
  val mockUserService = mock[UserService]

  "UserController" should {
    "login successfully" in new WithApplication() {
      mockUserService.checkLogin(LoginForm("test", "test")) returns Success(new User())

      val controller = new UserController(stubControllerComponents(), mockUserService)
      val request = FakeRequest(POST, "/login").withFormUrlEncodedBody("email" -> "test", "password" -> "test").withCSRFToken
      val result = controller.authenticate().apply(request)

      status(result) must equalTo(303)
    }

    "login fail" in new WithApplication() {
      mockUserService.checkLogin(LoginForm("test", "test")) returns Failure(new Exception(""))

      val controller = new UserController(stubControllerComponents(), mockUserService)
      val request = FakeRequest(POST, "/login").withFormUrlEncodedBody("email" -> "test", "password" -> "test").withCSRFToken
      val result = controller.authenticate().apply(request)

      contentAsString(result) must contain("login.fail")
    }

    "logout" in new WithApplication() {
      val controller = new UserController(stubControllerComponents(), mockUserService)
      val request = FakeRequest(GET, "/logout")
      val result = controller.logout().apply(request)

      status(result) must be_==(303)
    }

    "create new user # success" in new WithApplication() {
      mockUserService.createNewUser(RegisterForm("test", "test@gmail.com", "test")) returns ResultStatus.Succeed

      val controller = new UserController(stubControllerComponents(), mockUserService)
      val request = FakeRequest(POST, "/register/process").withFormUrlEncodedBody(
        "username" -> "test",
        "email" -> "test@gmail.com", "password" -> "test").withCSRFToken
      val result = controller.register().apply(request)
      status(result) must equalTo(303)
    }

    "create new user # failure" in new WithApplication() {
      mockUserService.createNewUser(RegisterForm("test", "test@gmail.com", "test")) returns ResultStatus.Failed

      val controller = new UserController(stubControllerComponents(), mockUserService)
      val request = FakeRequest(POST, "/register/process").withFormUrlEncodedBody(
        "username" -> "test",
        "email" -> "test@gmail.com", "password" -> "test").withCSRFToken
      val result = controller.register().apply(request)
      status(result) must equalTo(200)
      contentAsString(result) must contain("register.accout.exist")
    }
  }
}
