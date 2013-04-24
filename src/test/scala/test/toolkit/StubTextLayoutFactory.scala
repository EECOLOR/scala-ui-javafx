package test.toolkit

import com.sun.javafx.scene.text.TextLayoutFactory
import com.sun.javafx.scene.text.TextLayout
import org.specs2.mock.Mockito

class StubTextLayoutFactory extends TextLayoutFactory with Mockito {
  
    def createLayout():TextLayout = spy(new StubTextLayout)
    def getLayout():TextLayout = ???
    def disposeLayout(layout:TextLayout) = ???

}