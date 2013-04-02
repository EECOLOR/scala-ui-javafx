package ee.ui.application

import java.util.concurrent.CountDownLatch
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import ee.ui.implementation.ExitHandler

trait JavaFxLauncher extends ExitHandler { self: JavaFxApplicationLauncher =>

  val applicationRunning = new CountDownLatch(1)

  def launch(createApplication: () => Application with Engine, args: Array[String]) = {

    val platformStartup = new CountDownLatch(1)

    platformImplementation startup {
      platformStartup.countDown()
    }
    platformStartup.await()

    val promisedApplication = Promise[Application with Engine]

    def callExit =
      promisedApplication.future onSuccess {
        case application => application.exit()
      }

    platformImplementation onIdle callExit

    val application = createApplication()

    promisedApplication success application

    platformImplementation run {
      application.start()
    }

    applicationRunning.await()
  }

  def exit(application: Application) = {

    platformImplementation runAndWait {
      application.stop()
    }

    platformImplementation.exit()
    
    applicationRunning.countDown()
  }
}