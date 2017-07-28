package controllers

import javax.inject._

import forms.PostForm
import play.api.data.Form
import play.api.i18n.{ I18nSupport, Messages }
import play.api.mvc.{ Action, ControllerComponents, _ }
import play.filters.csrf.{ CSRFAddToken, CSRFCheck }
import play.i18n.MessagesApi
import services.PostService

@Singleton
class PostController @Inject() (controllerComponent: ControllerComponents, postService: PostService)
  extends AbstractController(controllerComponent) with I18nSupport with Secured {

  def index(): EssentialAction = withAuth { username => implicit request: Request[AnyContent] =>
    var list = postService.getAllPosts
    list match {
      case None                      => Ok(views.html.error())
      case list if list.get.isEmpty  => Ok(views.html.index(list.get, Messages("posts.nodata")))
      case list if !list.get.isEmpty => Ok(views.html.index(list.get, ""))
    }
  }

  def createPost(): EssentialAction = withAuth { username => implicit request: Request[AnyContent] =>

    PostForm.form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.createPost(formWithErrors))
      },
      form => {
        val authorId = request.session.get(IDSessionKey).get.toLong
        postService.createNewPost(form, authorId) match {
          case true => Redirect(routes.PostController.index())
          case _    => Ok(views.html.error())
        }
      }
    )
  }
}
