package test.toolkit

import com.sun.javafx.sg.PGRectangle

class StubRectangle extends StubShape with PGRectangle {
  
  def updateRectangle(x: Float, y: Float, width: Float, height: Float, arcWidth: Float, arcHeight: Float): Unit = {}
  
}