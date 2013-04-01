package ee.ui.application

import org.specs2.mutable.Specification
import java.util.concurrent.CountDownLatch
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import org.specs2.time.NoTimeConversions
import utils.ThreadUtils
import utils.SignatureTest

class PlatformImplementationTest extends Specification with NoTimeConversions with ThreadUtils {

  isolated

  "PlatformImplementation" should {
    "have a startup method with a callback" in {
      SignatureTest[PlatformImplementation, Unit](_.startup(callback = { /* I am a callback */ }))
    }
    "have an exit method" in {
      SignatureTest[PlatformImplementation, Unit](_.exit())
    }
    "have a runAndWait method" in {
      SignatureTest[PlatformImplementation, Unit](_.runAndWait(code = { /* I am a block of code that should be run */}))
    }
    "have a FinishListener which" should {
      "have an idle method" in {
        SignatureTest[PlatformImplementation.FinishListener, Unit](_.idle())
      }
      "have an exit method" in {
        SignatureTest[PlatformImplementation.FinishListener, Unit](_.exit())
      }
    }
    "have an addFinishListener method" in {
      SignatureTest[PlatformImplementation, Unit](_.addFinishListener(finishListener = new PlatformImplementation.FinishListener {
        def idle() = ???
        def exit() = ???
      }))
    }
    "have a default implementation in" in {
      DefaultPlatformImplementation must beAnInstanceOf[PlatformImplementation]
    }
  }
}