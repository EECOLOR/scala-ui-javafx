package ee.ui.implementation

import org.specs2.mutable.Specification
import com.sun.javafx.geom.BaseBounds
import ee.ui.display.primitives.Color
import ee.ui.primitives.Bounds
import javafx.scene.paint.{Color => JavaFxColor}
import javafx.scene.paint.{Paint => JavaFxPaint}
import utils.SignatureTest

object ConverterTest extends Specification with StubToolkit {
  
  xonly
  
  "Converter" should {
    
    "be able to convert paint" in {
      SignatureTest[Converter.type, JavaFxPaint, AnyRef](_ convert _)
      val color = JavaFxColor.rgb(1, 2, 3, 0.5)
      Converter.convert(color) === toolkit.getPaint(color)
    }
    
    "be able to convert a color" in {
      SignatureTest[Converter.type, Color, AnyRef](_ convert _)
      Converter.convert(Color(0x010203, 0.5)) === Converter.convert(JavaFxColor.rgb(1, 2, 3, 0.5))
    }
    
    "be able to convert bounds" in {
      SignatureTest[Converter.type, Bounds, BaseBounds](_ convert _)
      Converter.convert(Bounds(1, 2, 3, 4, 5, 6)) === BaseBounds.getInstance(1, 2, 3, 4, 5, 6)
    }
  }
  
}