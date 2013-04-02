package test.toolkit

import com.sun.javafx.tk.TKStage

class StubStage extends TKStage {
  var visible:Boolean = false
  
  def close(): Unit = ???
  def createTKScene(x$1: Boolean): com.sun.javafx.tk.TKScene = ???
  def grabFocus(): Boolean = ???
  def initSecurityContext(): Unit = ???
  def requestFocus(x$1: com.sun.javafx.tk.FocusCause): Unit = ???
  def requestFocus(): Unit = ???
  def setBounds(x$1: Float, x$2: Float, x$3: Boolean, x$4: Boolean, x$5: Float, x$6: Float, x$7: Float, x$8: Float, x$9: Float, x$10: Float): Unit = ???
  def setFullScreen(x$1: Boolean): Unit = ???
  def setIconified(x$1: Boolean): Unit = ???
  def setIcons(x$1: java.util.List[_]): Unit = ???
  def setImportant(x$1: Boolean): Unit = ???
  def setMaximumSize(x$1: Int, x$2: Int): Unit = ???
  def setMinimumSize(x$1: Int, x$2: Int): Unit = ???
  def setOpacity(x$1: Float): Unit = ???
  def setResizable(x$1: Boolean): Unit = ???
  def setScene(x$1: com.sun.javafx.tk.TKScene): Unit = ???
  def setTKStageListener(x$1: com.sun.javafx.tk.TKStageListener): Unit = ???
  def setTitle(x$1: String): Unit = ???
  def setVisible(value: Boolean): Unit = visible = value
  def toBack(): Unit = ???
  def toFront(): Unit = ???
  def ungrabFocus(): Unit = ???

}