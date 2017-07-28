import org.scalatest.selenium.HtmlUnit
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest

class ApplicationSpec extends PlaySpec with GuiceOneAppPerTest {
  "send 404 on a bad request" in new HtmlUnit {
    import java.net._
    val url = new URL("http://localhost:9000" + "/something")
    val con = url.openConnection().asInstanceOf[HttpURLConnection]
    try con.getResponseCode mustBe 404
    finally con.disconnect()
  }
}
