package models

import play.api.data.Form
import play.api.data.Forms._
/**
 * Created by hung_pt on 7/24/17.
 */

case class PostForm(title: String, content: String)

object PostForm {
  val form: Form[PostForm] = Form(
    mapping(
      "title" -> nonEmptyText,
      "content" -> nonEmptyText
    )(PostForm.apply)(PostForm.unapply)
  )
}