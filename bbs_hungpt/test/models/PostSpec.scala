package models

import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting

/**
 * Created by hung_pt on 7/21/17.
 */
class PostSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {

  "Post" should {
    "list post mustbe not empty" in {
      val listPost = Post.findAll()
      assert(listPost != Nil && listPost.size > 0)
    }

    "insert data to db" in {
      assert(Post.defaultData() == 1)
    }
  }
}
