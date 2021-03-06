package models

import forms.PostForm
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.Injecting

class PostSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {

  "Post" should {
    "list post mustbe not empty" in {
      val listPost = Post.findAll().get
      assert(listPost != Nil && listPost.size > 0)
    }

    "create new post" in {
      val result = Post.createPost(PostForm("test", "test"), 1)
      assert(result.get == 1)
    }
  }
}
