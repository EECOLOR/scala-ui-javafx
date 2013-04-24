package ee.ui.display.shapes

import org.specs2.mutable.Specification
import utils.SubtypeTest
import ee.ui.implementation.StubToolkit
import org.specs2.mock.Mockito
import com.sun.javafx.sg.PGText
import ee.ui.display.JavaFxShape
import ee.ui.members.ReadOnlyEvent
import ee.ui.system.RestrictedAccess
import javafx.scene.text.{TextAlignment => JavaFxTextAlignment}
import ee.ui.display.shapes.detail.TextAlignment
import ee.ui.primitives.VerticalPosition
import javafx.scene.text.Font

class JavaFxTextTest extends Specification with StubToolkit with Mockito {

  xonly
  isolated
  sequential

  val text = new Text
  val javaFxText = createJavaFxText

  def createJavaFxText = new JavaFxText(text)
  
  "JavaFxText" should {

    "extend JavaFxNode" in {
      SubtypeTest[JavaFxText <:< JavaFxShape]
    }

    "call toolkit.createPGText " resetToolkitMock {
      val javaFxText = createJavaFxText
      javaFxText.internalNode must beAnInstanceOf[PGText]
      there was one(stubToolkitMock).createPGText()
    }

    "call getTextLayoutFactory on toolkit" resetToolkitMock {
      val javaFxText = createJavaFxText
      there was one(stubToolkitMock).getTextLayoutFactory()
      there was atLeast(1)(stubToolkit.textLayoutFactory).createLayout()
    }

    "bind text to internal setGlyphs" in {
      there was one(javaFxText.layout).setContent("", Font.getDefault.impl_getNativeFont)
      text.text = "test"
      there was one(javaFxText.layout).setContent("test", Font.getDefault.impl_getNativeFont)
    }
    
    "bind textAlignement to internal setGlyphs" in {
      there was one(javaFxText.layout).setAlignment(JavaFxTextAlignment.LEFT.ordinal)
      text.textAlignment = TextAlignment.RIGHT
      there was one(javaFxText.layout).setAlignment(JavaFxTextAlignment.RIGHT.ordinal)
    }

    "call setGlyphs" in {
      there was one(javaFxText.internalNode).setGlyphs(javaFxText.layout.getRuns().asInstanceOf[Array[Object]])
    }
    
    "call setFont" in {
      there was one(javaFxText.internalNode).setFont(Font.getDefault.impl_getNativeFont)
    }
    
    "call setGlyphs when a related property changes" in {
      there was one(javaFxText.internalNode).setGlyphs(javaFxText.layout.getRuns().asInstanceOf[Array[Object]])
      text.text = "test"
      there were two(javaFxText.internalNode).setGlyphs(javaFxText.layout.getRuns().asInstanceOf[Array[Object]])
    }

    "set dirty to true, call setGlyphs and call boundsTransformed when a related property changes" in {
      var changesFired = 0

      javaFxText.dirty = false
      javaFxText.dirty.change { dirty =>
        if (dirty) {
          changesFired += 1
          javaFxText.dirty = false
        }
      }

      ReadOnlyEvent.fire(text.text.change, "test")(RestrictedAccess)
      ReadOnlyEvent.fire(text.textAlignment.change, TextAlignment.RIGHT)(RestrictedAccess)
      
      there were atLeast(2)(javaFxText.internalNode).setGlyphs(any[Array[AnyRef]])
      there were atLeast(2)(javaFxText.internalNode).setTransformedBounds(any, any)
      
      changesFired === 2
    }
  }
}