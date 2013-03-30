package ee.ui.application

import java.util.concurrent.CountDownLatch
import PlatformImplementation.FinishListener

trait JavaFxLauncher { self: JavaFxApplicationLauncher =>

  val applicationRunning = new CountDownLatch(1)

  def launch(createApplication: () => Application with Engine, args: Array[String]) = {

    val platformStarted = new CountDownLatch(1)

    platformImplementation startup {
      platformStarted.countDown()
    }

    platformStarted.await()

    platformImplementation addFinishListener new FinishListener {
      def idle() = ???
      def exit() = ???
    }

    val application = createApplication()

    application.start()

    applicationRunning.await()
  }

  def exit(application: Application) = {
    application.stop()
    applicationRunning.countDown()
  }
}