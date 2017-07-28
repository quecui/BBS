package services

import dtos.PostDTO
import forms.PostForm
import models.{ Post, User }
import org.joda.time.DateTime

import scala.util.{ Failure, Success, Try }

trait PostService {
  def getAllPosts: Option[List[PostDTO]]

  def createNewPost(postForm: PostForm, authorId: Long): Boolean
}

class PostServiceImpl extends PostService {
  override def getAllPosts: Option[List[PostDTO]] = {
    val listPost: Try[List[Post]] = Post.findAll() //new User(1, "hungpt", "1234", "hung_pt@septeni-technology.jp", new DateTime())
    var listPostDTO = List[PostDTO]()

    listPost match {
      case Success(listPost) =>
        listPostDTO = listPost.map(post => new PostDTO(post, User.getUserById(post.authorId).get.get))
        Some(listPostDTO)
      case Failure(exception) =>
        println("Error: " + exception.getMessage)
        None
    }
  }

  override def createNewPost(postForm: PostForm, authorId: Long): Boolean = {
    val result: Try[Int] = Post.createPost(postForm, authorId)

    result match {
      case Success(result) => true
      case Failure(exception) =>
        println("Error: " + exception.getMessage)
        false
    }

  }
}
