package controllers

import play.api.mvc._

trait Secured {
  val SecuredSessionKey = "username"
  val IDSessionKey = "authorId"

  def username(request: RequestHeader) = request.session.get(SecuredSessionKey)

  def withAuth(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(username, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }

  def onUnauthorized(request: RequestHeader) = Results.Redirect(routes.UserController.authenticate())
}
