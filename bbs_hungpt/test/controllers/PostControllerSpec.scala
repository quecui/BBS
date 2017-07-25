package controllers

import dto.PostDTO
import models.{Post, User}
import org.joda.time.DateTime
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import services.PostService
import utilbbs.ConstantData
/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */

class PostControllerSpec extends PlaySpec with GuiceOneAppPerTest with MockitoSugar {
  val mockPostService = mock[PostService]

  "HomeController" should {

    "show all post" in {
      val post = Post(1, "Test View List Post", "Content", new DateTime(), 1)
      val user = User(1, "hung_pt", "test@septeni-technology.jp", new DateTime())
      val postDTO = new PostDTO(post, user)
      when(mockPostService.getAllPosts) thenReturn List(postDTO)

      val controller = new PostController(stubControllerComponents(), mockPostService)
      val home = controller.index().apply(FakeRequest(GET, "/"))
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("All posts")
      contentAsString(home) mustNot include(ConstantData.NODATA)
    }

    "no post" in {
      when(mockPostService.getAllPosts) thenReturn List()

      val controller = new PostController(stubControllerComponents(), mockPostService)
      val home = controller.index().apply(FakeRequest(GET, "/"))
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include(ConstantData.NODATA)
    }

    "create default data" in {
      val controller = new PostController(stubControllerComponents(), mockPostService)
      val home = controller.createDefault().apply(FakeRequest(GET, "/data"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Data")
    }
  }
}