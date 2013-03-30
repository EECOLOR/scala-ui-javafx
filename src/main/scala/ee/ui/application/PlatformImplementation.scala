package ee.ui.application

import com.sun.javafx.application.PlatformImpl

trait PlatformImplementation {
  def startup(callback: => Unit): Unit
  def runAndWait(code: => Unit): Unit
  def exit(): Unit
  def addFinishListener(finishListener: PlatformImplementation.FinishListener): Unit
}

object PlatformImplementation {
  trait FinishListener {
    def idle(): Unit
    def exit(): Unit
  }
}

