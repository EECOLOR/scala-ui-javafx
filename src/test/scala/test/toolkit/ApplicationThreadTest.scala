package test.toolkit

import org.specs2.mutable.Specification
import utils.ThreadUtils
import org.specs2.time.NoTimeConversions
import scala.concurrent.duration._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

object ApplicationThreadTest extends Specification with ThreadUtils with NoTimeConversions {

  "ApplicationThread" should {
    "be able to run multiple pieces of code in a thread" in {
      val correctChildThreadName1 = WaitingBoolean(false, 1.second)
          val correctChildThreadName2 = WaitingBoolean(false, 1.second)

      val threadName = "MyThread"
      val childThreadName = "MyChildThread"

      Thread.currentThread setName threadName

      val a = new ApplicationThread

      val threadNamePromise = Promise[String]
      future {
        a run {
          Thread.currentThread setName childThreadName
          threadNamePromise success childThreadName
        }
      }
      
      Await.result(threadNamePromise.future, Duration.Inf)
      
      future {
        a run {
          correctChildThreadName1 set (Thread.currentThread.getName == childThreadName)
        }
      }

      future {
        a run {
          correctChildThreadName2 set (Thread.currentThread.getName == childThreadName)
        }
      }

      val correctThreadName = Thread.currentThread.getName == threadName

      correctThreadName and correctChildThreadName1.get and correctChildThreadName2.get
    }
  }

}