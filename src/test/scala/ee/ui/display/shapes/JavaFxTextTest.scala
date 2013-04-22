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

class JavaFxTextTest extends Specification with StubToolkit with Mockito {

  xonly
  isolated
  sequential

  val text = new Text
  val javaFxText = new JavaFxText(text)

  "JavaFxText" should {

    "extend JavaFxNode" in {
      SubtypeTest[JavaFxText <:< JavaFxShape]
    }

    "call toolkit.createPGText " resetToolkitMock {
      val javaFxText = new JavaFxText(text)
      javaFxText.internalNode must beAnInstanceOf[PGText]
      there was one(stubToolkitMock).createPGText()
    }

    "call getTextHelper on internal node" in {
      there was one(javaFxText.internalNode).getTextHelper()
    }

    "bind text to internal helper" in {
      there was one(javaFxText.helper).setText("")
      text.text = "test"
      there was one(javaFxText.helper).setText("test")
    }
    
    "bind textAlignement to internal helper" in {
      there was one(javaFxText.helper).setTextAlignment(JavaFxTextAlignment.LEFT.ordinal)
      text.textAlignment = TextAlignment.RIGHT
      there was one(javaFxText.helper).setTextAlignment(JavaFxTextAlignment.RIGHT.ordinal)
    }

    "call updateText" in {
      there was one(javaFxText.internalNode).updateText()
    }
    
    "call updateText when a related property changes" in {
      there was one(javaFxText.internalNode).updateText()
      text.text = "test"
      there were two(javaFxText.internalNode).updateText()
    }

    "set dirty to true, call updateText and call boundsTransformed when a related property changes" in {
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
      ReadOnlyEvent.fire(text.textOrigin.change, VerticalPosition.BOTTOM)(RestrictedAccess)
      
      there were atLeast(3)(javaFxText.internalNode).updateText()
      there were atLeast(3)(javaFxText.internalNode).setTransformedBounds(any)
      
      changesFired === 3
    }
  }
}