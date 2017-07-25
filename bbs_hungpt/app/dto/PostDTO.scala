package dto

import models.{ Post, User }

/**
 * Created by hung_pt on 7/20/17.
 */
class PostDTO(post: Post, user: User) {
  val id = post.id
  val title = post.title
  val content = post.content
  val createAt = post.createdAt
  val emailCreater: String = user.email
}
