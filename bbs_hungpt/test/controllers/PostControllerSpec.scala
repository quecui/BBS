package controllers

import dtos.PostDTO
import forms.PostForm
import models._
import org.joda.time.DateTime
import play.api.test._
import play.api.test.Helpers._
import org.specs2.mock.Mockito
import org.specs2.mutable._
import play.api.Application
import play.api.i18n.{ DefaultMessagesApi, Messages }
import play.api.inject.guice.GuiceApplicationBuilder
import scalikejdbc.config.DBs
import services.{ PostService, ResultStatus }
import play.api.test.CSRFTokenHelper._
import scala.util.{ Success }

class PostControllerSpec extends Specification with Mockito with Secured {
  val titlePost = "Demo testtttttttttttttttttttt s"
  val contentPost = "Nếu bạn đang cần phân tách rõ 2 side Query và Command trên hệ thống với mã nguồn đã dựng sẵn thì " +
    "bước này chắc hẳn là bước khó khăn nhất. Command: thực thi command là cách thức duy nhất để thay đổi state của " +
    "hệ thống. Command chịu trách nhiệm cho tất cả các thay đổi trong hệ thống. Nếu không có Command nào thực thi, " +
    "state của hệ thống sẽ giữ nguyên. Thực thi Command không nên trả về bất kỳ giá trị gì. " +
    "(Nên thực thi tương tự kiểu void function) Query: là thực thi việc đọc dữ liệu. " +
    "Nó đọc state của system và có thể filter, aggregate và chuyển đổi form data theo định dạng mong muốn. " +
    "Query nên trả về kết quả."

  val messagesApi = new DefaultMessagesApi(
    Map("en" ->
      Map("error.min" -> "minimum!")
    )
  )

  DBs.setupAll()
  val mockPostService = mock[PostService]

  "PostController#view post" should {
    "show all post when user logined" in new WithApplication() {
      val post = Post(1, "Test View List Post", "Content", new DateTime(), 1)
      val user = User(1, "hung_pt", "test", "test@septeni-technology.jp", new DateTime())
      val postDTO = new PostDTO(post, user)
      mockPostService.getAllPosts returns Success(List(postDTO))
      val controller = new PostController(stubControllerComponents(), mockPostService)
      val request = FakeRequest(GET, "/posts").withSession(SecuredSessionKey -> "hung_pt").withCSRFToken

      val result = controller.index().apply(request)
      status(result) must equalTo(OK)
      contentAsString(result) must contain("All posts")
      contentAsString(result) mustNotEqual contain("There are not no post at time")
    }

    "no post in DB with logined" in new WithApplication() {
      mockPostService.getAllPosts returns Success(List())

      val controller = new PostController(stubControllerComponents(), mockPostService)
      val request = FakeRequest(GET, "/posts").withSession(SecuredSessionKey -> "hung_pt").withCSRFToken
      implicit val messages = messagesApi.preferred(request)
      val result = controller.index().apply(request)
      status(result) must equalTo(OK)
      contentAsString(result) must contain(Messages("posts.nodata"))
    }

    "user doesn't login" in new WithApplication() {
      val post = Post(1, "Test View List Post", "Content", new DateTime(), 1)
      val user = User(1, "hung_pt", "test", "test@septeni-technology.jp", new DateTime())
      val postDTO = new PostDTO(post, user)
      mockPostService.getAllPosts returns Success(List(postDTO))
      val controller = new PostController(stubControllerComponents(), mockPostService)
      val request = FakeRequest(GET, "/posts").withSession().withCSRFToken

      val result = controller.index().apply(request)
      status(result) must equalTo(303)
    }
  }

  "PostController#create post" should {
    "create post successfully" in new WithApplication() {
      val application: Application = GuiceApplicationBuilder().build()
      mockPostService.createNewPost(PostForm(titlePost, contentPost), 1) returns Success(ResultStatus.Succeed)
      mockPostService.getAllPosts returns Success(List())
      val controller = new PostController(stubControllerComponents(), mockPostService)

      val request = FakeRequest(POST, routes.PostController.createPost().url).
        withFormUrlEncodedBody("title" -> titlePost, "content" -> contentPost).
        withSession(SecuredSessionKey -> "hung_pt").withSession(IDSessionKey -> "1").withFlash("status" -> "ok").
        withCSRFToken

      val result = controller.createPost().apply(request)
      status(result) must equalTo(303)
    }

    "create post error#wrong about length of title or content" in new WithApplication() {
      mockPostService.createNewPost(PostForm("", ""), 1) returns Success(ResultStatus.Failed)
      mockPostService.getAllPosts returns Success(List())

      val controller = new PostController(stubControllerComponents(), mockPostService)
      val request = FakeRequest(POST, "/post").withSession(SecuredSessionKey -> "hung_pt").
        withFormUrlEncodedBody("title" -> "", "content" -> "").withCSRFToken
      implicit val messages = messagesApi.preferred(request)
      val result = controller.createPost().apply(request)
      status(result) must equalTo(BAD_REQUEST)
    }
  }

}
