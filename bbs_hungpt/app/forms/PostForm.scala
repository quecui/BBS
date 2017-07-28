package forms

import play.api.data.Form
import play.api.data.Forms._

case class PostForm(title: String, content: String)

object PostForm {
  val form: Form[PostForm] = Form(
    mapping(
      "title" -> nonEmptyText,
      "content" -> nonEmptyText
    )(PostForm.apply)(PostForm.unapply)
  )
}