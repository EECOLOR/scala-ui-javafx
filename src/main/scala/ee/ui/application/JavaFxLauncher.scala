package ee.ui.application

import java.util.concurrent.CountDownLatch
import PlatformImplementation.FinishListener
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

trait JavaFxLauncher { self: JavaFxApplicationLauncher =>

  val applicationRunning = new CountDownLatch(1)

  def launch(createApplication: () => Application with Engine, args: Array[String]) = {

println("TODO: add a logging library so this can be logged")    
println("launch using " + platformImplementation)
    val platformStartup = new CountDownLatch(1)
println("calling startup")
    platformImplementation startup {
println("releasing lock")
      platformStartup.countDown()
    }
println("startup called")
    platformStartup.await()
println("platform started")

    val promisedApplication = Promise[Application with Engine]

println("adding finish listener")    
    platformImplementation addFinishListener new FinishListener {
      def idle() = 
          promisedApplication.future onSuccess {
            case application => JavaFxLauncher.this.exit(application)
          }
      
      def exit() = ???
    }
    
println("creating application")
    val application = createApplication()
println("application created")

    promisedApplication success application
    
println("starting application")
    application.start()
println("application started")

    applicationRunning.await()
println("application stopped")    
  }

  def exit(application: Application) = {
    application.stop()
    applicationRunning.countDown()
  }
}