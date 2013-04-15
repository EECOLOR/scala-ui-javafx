package test.toolkit

import com.sun.javafx.sg.PGShape

class StubShape extends StubNode with PGShape {
  
  def setFillPaint(paint: Any): Unit = {}
  def setMode(mode: PGShape.Mode): Unit = {}
      
  def setAntialiased(x$1: Boolean): Unit = ???
  def setDrawPaint(x$1: Any): Unit = ???
  def setDrawStroke(x$1: Float, x$2: com.sun.javafx.sg.PGShape.StrokeType, x$3: com.sun.javafx.sg.PGShape.StrokeLineCap, x$4: com.sun.javafx.sg.PGShape.StrokeLineJoin, x$5: Float, x$6: Array[Float], x$7: Float): Unit = ???

}