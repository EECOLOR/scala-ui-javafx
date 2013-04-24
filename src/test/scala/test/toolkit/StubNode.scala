package test.toolkit

import com.sun.javafx.sg.PGNode
import com.sun.javafx.geom.transform.BaseTransform
import com.sun.javafx.geom.BaseBounds

class StubNode extends PGNode {

  def setTransformMatrix(matrix: BaseTransform): Unit = {}
  def setTransformedBounds(bounds: BaseBounds, byTransformChangeOnly: Boolean): Unit = {}

  def release(): Unit = ??? 
  def effectChanged(): Unit = ???
  def setCachedAsBitmap(x$1: Boolean, x$2: com.sun.javafx.sg.PGNode.CacheHint): Unit = ???
  def setClipNode(x$1: com.sun.javafx.sg.PGNode): Unit = ???
  def setContentBounds(x$1: com.sun.javafx.geom.BaseBounds): Unit = ???
  def setDepthTest(x$1: Boolean): Unit = ???
  def setEffect(x$1: Any): Unit = ???
  def setNodeBlendMode(x$1: com.sun.scenario.effect.Blend.Mode): Unit = ???
  def setOpacity(x$1: Float): Unit = ???
  def setVisible(x$1: Boolean): Unit = ???

}