package ee.ui.application

import ee.ui.members.detail.Subscription
import com.sun.javafx.tk.TKListener
import java.util.{ List => JavaList }
import com.sun.javafx.tk.TKStage
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicBoolean
import ee.ui.members.Property
import ee.ui.members.ReadOnlySignal
import ee.ui.system.RestrictedAccess
import ee.ui.implementation.Toolkit
import java.util.concurrent.CountDownLatch
import ee.ui.implementation.ExitHandler

class DefaultPlatformImplementation extends PlatformImplementation with Toolkit {

  val onIdle = ReadOnlySignal()

  val windowCount = Property[Option[Int]](None)
  windowCount.change {
    case Some(count) if (count == 0) => ReadOnlySignal.fire(onIdle)(RestrictedAccess)
    case _ =>
  }  
  
  val started = Property(false)
  
  protected def r(code: => Unit):Runnable =
    new Runnable {
      def run = code
    }

  def defer(code: => Unit) = 
    // if we are on the correct thread we can execute the code directly
    if (toolkit.isFxUserThread) code
    else toolkit defer r(code) 
  
  def run(code: => Unit) = defer(code)
    

  def startup(callback: => Unit) = {

    require(!started, "Platform was already started")
    
    val toolkitListener =
      new TKListener() {
        def changedTopLevelWindows(windows: JavaList[TKStage]) =
          windowCount.value = Some(windows.size)
      }
    toolkit.addTkListener(toolkitListener);

    toolkit.startup(r(callback))

    started.value = true
  }

  def runAndWait(code: => Unit) = {
    val done = new CountDownLatch(1)
    defer {
      try {
        code
      } finally {
        done.countDown()
      }
    }
    done.await()
  }

  def exit() = {
    started.value = false
    toolkit.exit()
  }

}