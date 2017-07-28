package dtos

import models.{ Post, User }

class PostDTO(post: Post, user: User) {
  val id = post.id
  val title = post.title
  val content = post.content
  val createAt = post.createdAt
  val emailCreater: String = user.email
}
