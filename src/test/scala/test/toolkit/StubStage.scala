package test.toolkit

import com.sun.javafx.tk.TKStage
import scala.collection.mutable.ListBuffer
import com.sun.javafx.tk.TKStageListener
import com.sun.javafx.tk.TKScene
import org.specs2.mock.Mockito
import java.security.AccessControlContext

class StubStage extends TKStage with Mockito {

  var stageListener: TKStageListener = _

  def setVisible(value: Boolean): Unit = {}
  def setTitle(title: String): Unit = {}
  def setBounds(
    x: Float, y: Float, xSet: Boolean, ySet: Boolean,
    width: Float, height: Float, contentWidth: Float, contentHeight: Float,
    xGravity: Float, yGravity: Float): Unit = {}
  def setTKStageListener(listener: TKStageListener): Unit = stageListener = listener
  def createTKScene(depthBuffer: Boolean): TKScene = spy(new StubScene)
  def setScene(scene: TKScene): Unit = {}
  def setSecurityContext(accessControlContext: AccessControlContext): Unit = {}

  def close(): Unit = ???
  def grabFocus(): Boolean = ???
  def requestFocus(x$1: com.sun.javafx.tk.FocusCause): Unit = ???
  def requestFocus(): Unit = ???
  def setFullScreen(x$1: Boolean): Unit = ???
  def setIconified(x$1: Boolean): Unit = ???
  def setIcons(x$1: java.util.List[_]): Unit = ???
  def setImportant(x$1: Boolean): Unit = ???
  def setMaximumSize(x$1: Int, x$2: Int): Unit = ???
  def setMinimumSize(x$1: Int, x$2: Int): Unit = ???
  def setOpacity(x$1: Float): Unit = ???
  def setResizable(x$1: Boolean): Unit = ???
  def toBack(): Unit = ???
  def toFront(): Unit = ???
  def ungrabFocus(): Unit = ???
  def accessibleCreateBasicProvider(x$1: com.sun.javafx.accessible.providers.AccessibleProvider): Object = ???
  def accessibleCreateStageProvider(x$1: com.sun.javafx.accessible.providers.AccessibleStageProvider, x$2: Long): Object = ???
  def accessibleDestroyBasicProvider(x$1: Any): Unit = ???
  def accessibleFireEvent(x$1: Any, x$2: Int): Unit = ???
  def accessibleFirePropertyChange(x$1: Any, x$2: Int, x$3: Boolean, x$4: Boolean): Unit = ???
  def accessibleFirePropertyChange(x$1: Any, x$2: Int, x$3: Int, x$4: Int): Unit = ???
  def accessibleInitIsComplete(x$1: Any): Unit = ???
  def releaseInput(): Unit = ???
  def requestInput(x$1: String, x$2: Int, x$3: Double, x$4: Double, x$5: Double, x$6: Double, x$7: Double, x$8: Double, x$9: Double, x$10: Double, x$11: Double, x$12: Double, x$13: Double, x$14: Double, x$15: Double, x$16: Double): Unit = ???
  def setMaximized(x$1: Boolean): Unit = ???
  def setRTL(x$1: Boolean): Unit = ???
}