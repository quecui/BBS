package controllers

import javax.inject.Inject

import forms.{ LoginForm, RegisterForm }
import models.User
import play.api.i18n.{ I18nSupport, Messages }
import play.api.mvc._
import play.i18n.MessagesApi
import services.{ PostService, ResultStatus, UserService }

class UserController @Inject() (controllerComponent: ControllerComponents, userService: UserService)
  extends AbstractController(controllerComponent) with I18nSupport with Secured {

  def register(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    RegisterForm.form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.register(formWithErrors, ""))

      },
      form => {
        userService.createNewUser(form) match {
          case ResultStatus.Succeed => Redirect(routes.PostController.index())
          case ResultStatus.Failed  => Ok(views.html.register(RegisterForm.form, Messages("register.accout.exist")))
          case _                    => Ok(views.html.error())
        }
      }
    )

  }

  def authenticate(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    LoginForm.form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.login(formWithErrors, ""))

      },
      form => {
        val user = userService.checkLogin(form)
        user match {
          case None                        => Ok(views.html.error())
          case user if user.get.name == "" => Ok(views.html.login(LoginForm.form, Messages("login.fail")))
          case user if user.get.name != "" => Redirect(routes.PostController.index()).
            withSession(SecuredSessionKey -> user.get.name).addingToSession(IDSessionKey -> user.get.id.toString)
        }
      }
    )
  }

  def logout(): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.UserController.authenticate()).withSession()
  }
}
