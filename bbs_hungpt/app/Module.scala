import com.google.inject.AbstractModule
import services.{ PostService, PostServiceImpl, UserService, UserServiceImpl }

class Module extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[PostService]).to(classOf[PostServiceImpl])

    bind(classOf[UserService]).to(classOf[UserServiceImpl])
  }

}
