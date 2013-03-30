package ee.ui.application

import com.sun.javafx.application.PlatformImpl

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

  def addFinishListener(finishListener: PlatformImplementation.FinishListener) =
    PlatformImpl addListener new PlatformImpl.FinishListener {
      def exitCalled() = finishListener.exit()
      def idle(javaFxImplicitExit: Boolean) = // note that we ignore the JavaFx implicit exit setting 
        ??? //finishListener.idle()  
    }
}