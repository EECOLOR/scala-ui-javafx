package test.toolkit

import com.sun.javafx.sg.PGText
import com.sun.javafx.sg.PGTextHelper
import org.specs2.mock.Mockito

class StubText extends StubShape with PGText with Mockito {

  def getTextHelper(): PGTextHelper = spy(new StubTextHelper)
  def updateText(): Unit = {}

}