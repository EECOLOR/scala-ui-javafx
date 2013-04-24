package test.toolkit

import com.sun.javafx.tk.FontLoader
import javafx.scene.text.Font

class StubFontLoader extends FontLoader {

  def getSystemFontSize(): Float = 12
  
  def loadFont(font: Font): Unit = {
    val name = font.getName
    name match {
      case "System Regular" => font.impl_setNativeFont(font, name, "System", "Regular")
    }
  }

  def computeStringWidth(x$1: String, x$2: javafx.scene.text.Font): Float = ???
  def font(x$1: Any, x$2: Float): javafx.scene.text.Font = ???
  def font(x$1: String, x$2: javafx.scene.text.FontWeight, x$3: javafx.scene.text.FontPosture, x$4: Float): javafx.scene.text.Font = ???
  def getFamilies(): java.util.List[String] = ???
  def getFontMetrics(x$1: javafx.scene.text.Font): com.sun.javafx.tk.FontMetrics = ???
  def getFontNames(x$1: String): java.util.List[String] = ???
  def getFontNames(): java.util.List[String] = ???

}