package controllers

import javax.inject._

import models.PostForm
import play.api.i18n.I18nSupport
import play.api.mvc.{ Action, ControllerComponents, _ }
import services.PostService
import utilbbs.ConstantData

@Singleton
class PostController @Inject() (controllerComponent: ControllerComponents, postService: PostService)
  extends AbstractController(controllerComponent) with I18nSupport {

  def index(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    var list = postService.getAllPosts

    list match {
      case list if list.isEmpty  => Ok(views.html.index(list, ConstantData.NODATA))
      case list if !list.isEmpty => Ok(views.html.index(list, ""))
      case _                     => Ok(views.html.error())
    }
  }

  def createPost(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    PostForm.form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.createPost(formWithErrors))
      },
      form => {
        postService.createNewPost(form)
        Redirect(routes.PostController.index())
      }
    )
  }
}
