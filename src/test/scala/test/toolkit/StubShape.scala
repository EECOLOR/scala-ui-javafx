package test.toolkit

import com.sun.javafx.sg.PGShape

class StubShape extends StubNode with PGShape {
  
  def setFillPaint(paint: Any): Unit = {}
  def setMode(mode: PGShape.Mode): Unit = {}
  def setDrawStroke(thickness: Float, tpe: PGShape.StrokeType, lineCap: PGShape.StrokeLineCap, lineJoin: PGShape.StrokeLineJoin, miterLimit: Float, dashArray: Array[Float], dashOffset: Float): Unit = {}
  def setDrawPaint(paint: Any): Unit = {}
      
  def setAntialiased(x$1: Boolean): Unit = ???

}