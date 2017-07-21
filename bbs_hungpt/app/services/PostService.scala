package services


import dto.PostDTO
import models.{Post, User}
import org.joda.time.DateTime

/**
  * Created by hung_pt on 7/20/17.
  */
trait PostService {
  def getAllPosts: List[PostDTO]
  def setDefaultValue: Unit
}

class PostServiceImpl extends PostService{
  override def getAllPosts: List[PostDTO] = {
    var listPost: List[Post] = Post.findAll()
    var listPostDTO = List[PostDTO]()

    for (i <- 0 until listPost.length){
      //Find User By ID
      var user = new User(1, "hungpt", "hung_pt@septeni-technology.jp", new DateTime())
      listPostDTO ::= new PostDTO(listPost(i), user)
    }

    listPostDTO
  }

  override def setDefaultValue: Unit = {
    Post.defaultData
  }
}
