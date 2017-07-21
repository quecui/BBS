package controllers

import javax.inject._

import models.Post
import play.api.mvc.{ControllerComponents, _}
import scalikejdbc._
import scalikejdbc.config._
import services.{PostService, PostServiceImpl}
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  implicit val session = AutoSession
  val postService: PostService = new PostServiceImpl

  def index() = Action { implicit request: Request[AnyContent] =>
    var list = postService.getAllPosts
    Ok(views.html.index(list))
  }

  def createDefault() = Action{ implicit request: Request[AnyContent] =>
    postService.setDefaultValue
    Ok(views.html.default())
  }

}
