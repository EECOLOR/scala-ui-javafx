package ee.ui.application

import com.sun.javafx.application.PlatformImpl
import ee.ui.members.detail.Subscription
import ee.ui.members.ReadOnlySignal

trait PlatformImplementation {
  def startup(callback: => Unit): Unit
  def runAndWait(code: => Unit): Unit
  def run(code: => Unit): Unit
  def exit(): Unit
  def onIdle:ReadOnlySignal
}
