package test.toolkit

import com.sun.javafx.tk.TKStage
import scala.collection.mutable.ListBuffer
import com.sun.javafx.tk.TKStageListener

class StubStage extends TKStage {

  var stageListener:TKStageListener = _
  
  def setVisible(value: Boolean): Unit = {}
  def setTitle(title: String): Unit = {}
  def setBounds(
      x: Float, y: Float, xSet: Boolean, ySet: Boolean, 
      width: Float, height: Float, contentWidth: Float, contentHeight: Float, 
      xGravity: Float, yGravity: Float): Unit = {}
  def setTKStageListener(listener: TKStageListener): Unit = stageListener = listener
  
  def close(): Unit = ???
  def createTKScene(x$1: Boolean): com.sun.javafx.tk.TKScene = ???
  def grabFocus(): Boolean = ???
  def initSecurityContext(): Unit = ???
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
  def setScene(x$1: com.sun.javafx.tk.TKScene): Unit = ???
  def toBack(): Unit = ???
  def toFront(): Unit = ???
  def ungrabFocus(): Unit = ???

}