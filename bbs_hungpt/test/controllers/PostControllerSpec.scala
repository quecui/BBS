package controllers

import dtos.PostDTO
import models.{ Post, User }
import org.joda.time.DateTime
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.i18n.{ I18nSupport, Messages }
import play.api.i18n.Messages.Message
import play.api.test.Helpers._
import play.api.test._
import play.i18n.MessagesApi
import services.PostService

class PostControllerSpec extends PlaySpec with GuiceOneAppPerTest with MockitoSugar with Secured {
  val mockPostService = mock[PostService]

  "PostController" should {
    "show all post with user login" in {
      val post = Post(1, "Test View List Post", "Content", new DateTime(), 1)
      val user = User(1, "hung_pt", "test", "test@septeni-technology.jp", new DateTime())
      val postDTO = new PostDTO(post, user)
      when(mockPostService.getAllPosts) thenReturn Some(List(postDTO))

      val controller = new PostController(stubControllerComponents(), mockPostService)
      val home = controller.index().apply(FakeRequest(GET, "/posts").withSession(SecuredSessionKey -> "hung_pt"))
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("All posts")
      contentAsString(home) mustNot include(Messages("posts.nodata"))
    }

    "show all post with user doesn't login" in {
      val post = Post(1, "Test View List Post", "Content", new DateTime(), 1)
      val user = User(1, "hung_pt", "test", "test@septeni-technology.jp", new DateTime())
      val postDTO = new PostDTO(post, user)
      when(mockPostService.getAllPosts) thenReturn Some(List(postDTO))

      val controller = new PostController(stubControllerComponents(), mockPostService)
      val home = controller.index().apply(FakeRequest(GET, "/posts"))
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Not registered?")
    }

    "no post" in {
      when(mockPostService.getAllPosts) thenReturn Some(List())

      val controller = new PostController(stubControllerComponents(), mockPostService)
      val home = controller.index().apply(FakeRequest(GET, "/posts").withSession(SecuredSessionKey -> "hung_pt"))
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include(Messages("posts.nodata"))
    }
  }
}