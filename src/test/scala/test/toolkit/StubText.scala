package test.toolkit

import com.sun.javafx.sg.PGText
import org.specs2.mock.Mockito

class StubText extends StubShape with PGText with Mockito {

  def setGlyphs(glyphs: Array[Object]): Unit = {}
  def setFont(internalFont: Any): Unit = {}
  
  def setFontSmoothingType(x$1: Int): Unit = ???
  def setLayoutLocation(x$1: Float, x$2: Float): Unit = ???
  def setSelection(x$1: Int, x$2: Int, x$3: Any): Unit = ???
  def setStrikethrough(x$1: Boolean): Unit = ???
  def setUnderline(x$1: Boolean): Unit = ???

}