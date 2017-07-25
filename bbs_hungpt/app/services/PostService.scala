package services

import dto.PostDTO
import models.{ Post, PostForm, User }
import org.joda.time.DateTime

import scala.util.{ Failure, Success, Try }

/**
 * Created by hung_pt on 7/20/17.
 */
trait PostService {
  def getAllPosts: List[PostDTO]

  def createNewPost(postForm: PostForm): Int
}

class PostServiceImpl extends PostService {
  override def getAllPosts: List[PostDTO] = {
    val listPost: Try[List[Post]] = Post.findAll()

    var listPostDTO = List[PostDTO]()

    listPost match {
      case Success(v) =>
        listPostDTO = listPost.get.map(post => new PostDTO(post, new User(1, "hungpt", "hung_pt@septeni-technology.jp", new DateTime())))
    }

    listPostDTO.reverse
  }

  override def createNewPost(postForm: PostForm): Int = {
    Post.createPost(postForm).get
  }
}
