package test.toolkit

import com.sun.javafx.sg.PGRectangle

class StubRectangle extends PGRectangle {
  
  def effectChanged(): Unit = ???
  def setCachedAsBitmap(x$1: Boolean, x$2: com.sun.javafx.sg.PGNode.CacheHint): Unit = ???
  def setClipNode(x$1: com.sun.javafx.sg.PGNode): Unit = ???
  def setContentBounds(x$1: com.sun.javafx.geom.BaseBounds): Unit = ???
  def setDepthTest(x$1: Boolean): Unit = ???
  def setEffect(x$1: Any): Unit = ???
  def setNodeBlendMode(x$1: com.sun.scenario.effect.Blend.Mode): Unit = ???
  def setOpacity(x$1: Float): Unit = ???
  def setTransformMatrix(x$1: com.sun.javafx.geom.transform.BaseTransform): Unit = ???
  def setTransformedBounds(x$1: com.sun.javafx.geom.BaseBounds): Unit = ???
  def setVisible(x$1: Boolean): Unit = ???

  // Members declared in com.sun.javafx.sg.PGRectangle
  def updateRectangle(x$1: Float, x$2: Float, x$3: Float, x$4: Float, x$5: Float, x$6: Float): Unit = ???

  // Members declared in com.sun.javafx.sg.PGShape
  def setAntialiased(x$1: Boolean): Unit = ???
  def setDrawPaint(x$1: Any): Unit = ???
  def setDrawStroke(x$1: Float, x$2: com.sun.javafx.sg.PGShape.StrokeType, x$3: com.sun.javafx.sg.PGShape.StrokeLineCap, x$4: com.sun.javafx.sg.PGShape.StrokeLineJoin, x$5: Float, x$6: Array[Float], x$7: Float): Unit = ???
  def setFillPaint(x$1: Any): Unit = ???
  def setMode(x$1: com.sun.javafx.sg.PGShape.Mode): Unit = ???

}