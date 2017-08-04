package services

import dtos.PostDTO
import forms.PostForm
import models.{ Post, User }
import org.joda.time.DateTime

import scala.util.{ Failure, Success, Try }

trait PostService {
  def getAllPosts: Try[List[PostDTO]]

  def createNewPost(postForm: PostForm, authorId: Long): Try[ResultStatus.Value]
}

class PostServiceImpl extends PostService {
  override def getAllPosts: Try[List[PostDTO]] = Try {
    Post.findAll().map(posts => posts.map(post => new PostDTO(post, User.getUserById(post.authorId).get.get))).
      getOrElse(throw new Exception("Don't get all posts"))
  }

  override def createNewPost(postForm: PostForm, authorId: Long): Try[ResultStatus.Value] = Try {
    Post.createPost(postForm, authorId).map(tryResult => ResultStatus.Succeed).getOrElse(throw new Exception("Don't create new post"))
  }
}
