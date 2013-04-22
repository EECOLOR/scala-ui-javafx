package test.toolkit

import com.sun.javafx.tk.FontLoader

class StubFontLoader extends FontLoader {

  def loadFont(font: javafx.scene.text.Font): Unit = ???
  /*
  {
    val name = font.getName.toLowerCase
    name match {
      case "system" | "system regular" => font.impl_setNativeFont(font, font.getName, "System", "Regular")
    }
  }
  */

  def computeStringWidth(x$1: String, x$2: javafx.scene.text.Font): Float = ???
  def font(x$1: Any, x$2: Float): javafx.scene.text.Font = ???
  def font(x$1: String, x$2: javafx.scene.text.FontWeight, x$3: javafx.scene.text.FontPosture, x$4: Float): javafx.scene.text.Font = ???
  def getFamilies(): java.util.List[String] = ???
  def getFontMetrics(x$1: javafx.scene.text.Font): com.sun.javafx.tk.FontMetrics = ???
  def getFontNames(x$1: String): java.util.List[String] = ???
  def getFontNames(): java.util.List[String] = ???
  def getSystemFontSize(): Float = ???

}