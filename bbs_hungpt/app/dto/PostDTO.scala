package dto

import models.{Post, User}

import scala.collection.mutable.ListBuffer

/**
  * Created by hung_pt on 7/20/17.
  */
class PostDTO(post: Post, user: User){
  var id = post.id
  var title = post.title
  var content = post.content
  var createAt = post.createdAt
  var emailCreater: String = user.email
}
