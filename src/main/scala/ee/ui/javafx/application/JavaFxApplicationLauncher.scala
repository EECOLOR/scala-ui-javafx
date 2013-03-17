package ee.ui.javafx.application

import ee.ui.application.Application
import ee.ui.application.ApplicationLauncher
import ee.ui.javafx.nativeImplementation.NativeManager
import ee.ui.events.PulseEvent
import com.sun.javafx.tk.TKPulseListener
import javafx.scene.shape.Path
import ee.ui.primitives.Point
import com.sun.javafx.geom.Path2D
import javafx.util.Pair
import ee.ui.javafx.nativeImplementation.Converters
import ee.ui.primitives.Font
import ee.ui.primitives.FontMetrics
import ee.ui.system.ClipBoard
import ee.ui.display.text.TextHelper
import ee.ui.display.Text
import ee.ui.system.DataFormat
import scala.language.implicitConversions
import ee.ui.application.details.ApplicationDependencies
import ee.ui.application.details.ImplementationContract

trait JavaFxApplicationLauncher extends ApplicationLauncher {
  
  lazy val applicationDependencies = new ApplicationDependencies {
    val implementationContract = new ImplementationContract with Toolkit {

      lazy val launcher = new Launcher(DefaultPlatformImplementation)
      lazy val displayImplementationHandler = new NativeManager(applicationCreated)
      lazy val pulseEvent = JavaFxPulseEvent

      lazy val textHelper = new TextHelper {
        def getCaretPosition(text: Text, index: Int): Point = {
          val nativeShape = displayImplementationHandler(text).helper getCaretShape (index, false)
          val pathElements = toolkit convertShapeToFXPath nativeShape
          val caretPath = new Path2D()
          pathElements foreach (_ impl_addTo caretPath)
          
          val boundingBox = Array[Float](0, 0, 0, 0)

          com.sun.javafx.geom.Shape accumulate (boundingBox, caretPath, null);

          val Array(minX, minY, maxX, maxY) = boundingBox
          
          Point((minX + maxX) / 2, (minY + maxY) / 2)
        }

        def getCaretIndex(text: Text, position: Point): Int = {
          val nativeHitInfo = displayImplementationHandler(text).helper getHitInfo (position.x.toFloat, position.y.toFloat)
          
          (toolkit convertHitInfoToFX nativeHitInfo).getCharIndex
        }
        
        def getFontMetrics(font:Font):FontMetrics = {
          val javaFxFont = Converters convertFont font
          val javaFxFontMetrics = toolkit.getFontLoader getFontMetrics javaFxFont
          Converters convertFontMetrics javaFxFontMetrics
        }
        
      }
      
      lazy val systemClipBoard = new ClipBoard {
        lazy val systemClipBoard = toolkit.getSystemClipboard()
        
        implicit def dataFormat(d:DataFormat) = Converters convertDataFormat d
        
        def set(key:DataFormat, value:AnyRef): Boolean =
          systemClipBoard putContent new Pair(key, value)

        def get(key: DataFormat): Option[AnyRef] =
          Option(systemClipBoard getContent key)

        def contains(key:DataFormat):Boolean =
          systemClipBoard hasContent key
      }
    }
  }
}

object JavaFxPulseEvent extends PulseEvent with TKPulseListener with Toolkit {

  toolkit addStageTkPulseListener this

  def pulse() = fire
}