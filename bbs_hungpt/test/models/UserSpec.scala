package models

import forms.RegisterForm
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import org.specs2.mutable.Specification
import play.api.test.{ Injecting, WithApplication }
import scalikejdbc.config.DBs

class UserSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  DBs.setupAll()

  "User Model" should {
    "create New User" in new WithApplication() {
      val user = User.createUser(RegisterForm("storm", "hungpt59.uet@gmail.com", "hunghp1502"))
      assert(user.get == 1)
    }

    "Get User By Email" in new WithApplication() {
      val user = User.getUserByEmail("hung_pt@septeni-technology.jp")
      assert(user != None)
    }

    "GetUserByEmailAndPass" in new WithApplication() {
      val user = User.getUserByEmailAndPass("hung_pt@septeni-technology.jp", "hunghp1502")
      assert(user != None)
    }

    "GetUserById" in new WithApplication() {
      val user = User.getUserById(1)
      assert(user != None)
    }
  }

}
