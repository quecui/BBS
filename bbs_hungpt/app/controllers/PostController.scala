package controllers

import javax.inject._

import dtos.PostDTO
import forms.PostForm
import play.api.i18n.{ I18nSupport, Messages }
import play.api.mvc.{ Action, ControllerComponents, _ }
import services.{ PostService, PostServiceImpl, ResultStatus }

import scala.util.{ Failure, Success, Try }

@Singleton
class PostController @Inject() (controllerComponent: ControllerComponents, postService: PostService)
  extends AbstractController(controllerComponent) with I18nSupport with Secured {

  def index() = Action { implicit request: Request[AnyContent] =>
    postService.getAllPosts match {
      case Success(posts) => posts match {
        case posts if posts.isEmpty  => Ok(views.html.index(posts, PostForm.form, Messages("posts.nodata")))
        case posts if !posts.isEmpty => Ok(views.html.index(posts, PostForm.form, ""))
      }
      case _ => Ok(views.html.error())
    }
  }

  def createPost() = Action { implicit request: Request[AnyContent] =>
    PostForm.form.bindFromRequest.fold(
      formWithErrors => {
        val list = postService.getAllPosts.get
        BadRequest(views.html.index(list, formWithErrors, ""))
      },
      form => {
        val authorId = request.session.get(IDSessionKey).get.toLong
        postService.createNewPost(form, authorId) match {
          case Success(result) if result == ResultStatus.Succeed => Redirect("/posts").flashing("status" -> "ok")
          case _ => Ok(views.html.error())
        }
      }

    )
  }
}
