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
import ee.ui.members.ReadOnlySignal

class PlatformImplementationTest extends Specification with NoTimeConversions with ThreadUtils {

  isolated
  xonly

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
    "have a run method" in {
      SignatureTest[PlatformImplementation, Unit](_.run(code = { /* I am a block of code that should be run */}))
    }
    "have an onIdle signal" in {
      SignatureTest[PlatformImplementation, ReadOnlySignal](_.onIdle)
    }
    "have a default implementation in" in {
      new DefaultPlatformImplementation must beAnInstanceOf[PlatformImplementation]
    }
  }
}