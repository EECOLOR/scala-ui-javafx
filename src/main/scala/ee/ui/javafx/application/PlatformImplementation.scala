package ee.ui.javafx.application

import com.sun.javafx.application.PlatformImpl
import javafx.application.ConditionalFeature

trait PlatformImplementation {
  def exit() = PlatformImpl.exit()
  def startup(r:Runnable) = PlatformImpl startup r
  def addListener(l:Listener) = PlatformImpl addListener l
  def removeListener(l:Listener) = PlatformImpl removeListener l
  def runAndWait(r:Runnable) = PlatformImpl runAndWait r
  def isSupported(c:ConditionalFeature) = PlatformImpl isSupported c
  def tkExit() = PlatformImpl.tkExit()
  
  type Listener = PlatformImpl.FinishListener
}

object DefaultPlatformImplementation extends PlatformImplementation