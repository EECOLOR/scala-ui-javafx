package ee.ui.application

import org.specs2.mutable.Specification
import utils.ThreadUtils
import PlatformImplementation.FinishListener

class DefaultPlatformImplementationTest extends Specification with ThreadUtils {
  
  isolated
  /* we need to execute sequential because DefaultPlatformImplementation 
   * is backed by a static (stateful) class
   */
  sequential 
  
  "DefaultImplementation" should {
    "call the callback when startup is called" in {
      val callbackCalled = WaitingBoolean(false)

      DefaultPlatformImplementation startup {
        callbackCalled set true
      }

      callbackCalled.get === true
    }
    "execute the code on the JavaFx application thread" in {
      var threadName = ""
        
      DefaultPlatformImplementation runAndWait {
        threadName = Thread.currentThread.getName()
      }
          
      threadName === "JavaFX Application Thread"
    }
    
    "call the FinishListener on exit" in {
      var exitCalled = false
      DefaultPlatformImplementation addFinishListener new FinishListener {
        def idle() = ???
        def exit() = exitCalled = true
      }
      DefaultPlatformImplementation.exit()

      exitCalled === true
    }
    "call the FinishListener on idle" in {
      /*
       * In order for idle to trigger, we need to open and close a window
       */
      
      var idleCalled = false
      
      DefaultPlatformImplementation addFinishListener new FinishListener {
        def idle() = idleCalled = true
        def exit() = ???
      }
      
      DefaultPlatformImplementation runAndWait {
        // do nothing
      }
      
      Thread.sleep(1000)

      //idleCalled === true
      todo //refactor Thread.sleep in ThreadUtils
    }
  }
}