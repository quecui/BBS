package services

import org.scalatest.mockito._
import org.scalatestplus.play.PlaySpec

/**
 * Created by hung_pt on 7/21/17.
 */
class PostServiceSpec extends PlaySpec with MockitoSugar {
  val postService = new PostServiceImpl

  "Post Service Impl" should {
    "list preview post not empty" in {
      val listPost = postService.getAllPosts
      assert(listPost != Nil && listPost.length > 0)
    }

    "setDefaultValue()" in {
      assert(postService.setDefaultValue == 1)
    }
  }
}
