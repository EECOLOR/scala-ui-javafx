package ee.ui.application

import com.sun.javafx.application.PlatformImpl
import ee.ui.members.detail.Subscription

object DefaultPlatformImplementation extends PlatformImplementation {
  def run(code: => Unit) =
    new Runnable {
      def run = code
    }

  def startup(callback: => Unit) =
    PlatformImpl startup run(callback)

  def runAndWait(code: => Unit) =
    PlatformImpl runAndWait run(code)

  def exit() = PlatformImpl.exit()

  def addFinishListener(finishListener: PlatformImplementation.FinishListener) = {
    val listener =
      new PlatformImpl.FinishListener {
        def exitCalled() = finishListener.exit()
        def idle(javaFxImplicitExit: Boolean) = // note that we ignore the JavaFx implicit exit setting 
          finishListener.idle()
      }

    PlatformImpl addListener listener

    new Subscription {
      def unsubscribe() = PlatformImpl removeListener listener
    }
  }

}