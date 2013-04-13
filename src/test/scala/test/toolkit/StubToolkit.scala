package test.toolkit

import com.sun.javafx.tk.Toolkit
import java.util.concurrent.atomic.AtomicBoolean
import javafx.stage.StageStyle
import javafx.stage.Modality
import com.sun.javafx.tk.TKStage
import org.specs2.mock.Mockito
import com.sun.javafx.sg.PGRectangle

object StubToolkit extends StubToolkit {
  def setFxUserThread(t: Thread) = Toolkit.setFxUserThread(t)
}

class StubToolkit extends Toolkit with Mockito {

  val shadowMock = mock[StubToolkit].smart

  val applicationThread = new ApplicationThread

  def init(): Boolean = true

  val started = new AtomicBoolean(false)

  def startup(r: Runnable): Unit = {
    shadowMock startup r
    
    if (started.getAndSet(true)) throw new RuntimeException("Can not start, already started, call exit first")
    
    applicationThread run {
      StubToolkit.setFxUserThread(Thread.currentThread)
      r.run
    }
  }

  def defer(r: Runnable): Unit = {
    shadowMock defer r
    
    applicationThread run {
      r.run
    }
  }

  override def exit() = {
    shadowMock.exit()
    
    super.exit()
    started.set(false)
  }

  def createTKStage(
      stageStyle: StageStyle, 
      primary: Boolean, 
      modality: Modality, 
      ownerStage: TKStage): TKStage = {
    shadowMock.createTKStage(stageStyle, primary, modality, ownerStage)
    
    spy(new StubStage)
  }

  def createPGRectangle(): PGRectangle = {
    shadowMock.createPGRectangle()
    spy(new StubRectangle) 
  }
  
  
  def waitFor(x$1: com.sun.javafx.tk.Toolkit.Task): Unit = ???

  def enterNestedEventLoop(x$1: Any): Object = ???
  def exitNestedEventLoop(x$1: Any, x$2: Any): Unit = ???

  def accumulateStrokeBounds(x$1: com.sun.javafx.geom.Shape, x$2: Array[Float], x$3: com.sun.javafx.sg.PGShape.StrokeType, x$4: Double, x$5: com.sun.javafx.sg.PGShape.StrokeLineCap, x$6: com.sun.javafx.sg.PGShape.StrokeLineJoin, x$7: Float, x$8: com.sun.javafx.geom.transform.BaseTransform): Unit = ???

  def convertDragRecognizedEventToFX(x$1: Any, x$2: javafx.scene.input.Dragboard): javafx.scene.input.DragEvent = ???
  def convertDragSourceEventToFX(x$1: Any, x$2: javafx.scene.input.Dragboard): javafx.scene.input.DragEvent = ???
  def convertDropTargetEventToFX(x$1: Any, x$2: javafx.scene.input.Dragboard): javafx.scene.input.DragEvent = ???
  def convertHitInfoToFX(x$1: Any): com.sun.javafx.scene.text.HitInfo = ???
  def convertInputMethodEventToFX(x$1: Any): javafx.scene.input.InputMethodEvent = ???
  def convertKeyEventToFX(x$1: Any): javafx.scene.input.KeyEvent = ???
  def convertMouseEventToFX(x$1: Any): javafx.scene.input.MouseEvent = ???
  def convertShapeToFXPath(x$1: Any): Array[javafx.scene.shape.PathElement] = ???

  protected def createColorPaint(x$1: javafx.scene.paint.Color): Object = ???
  protected def createImagePatternPaint(x$1: javafx.scene.paint.ImagePattern): Object = ???
  protected def createLinearGradientPaint(x$1: javafx.scene.paint.LinearGradient): Object = ???
  protected def createRadialGradientPaint(x$1: javafx.scene.paint.RadialGradient): Object = ???

  def createDragboard(): javafx.scene.input.Dragboard = ???
  def enableDrop(x$1: com.sun.javafx.tk.TKScene, x$2: com.sun.javafx.tk.TKDropTargetListener): Unit = ???
  def startDrag(x$1: Any, x$2: java.util.Set[javafx.scene.input.TransferMode], x$3: com.sun.javafx.tk.TKDragSourceListener, x$4: javafx.scene.input.Dragboard): Unit = ???

  def createPGArc(): com.sun.javafx.sg.PGArc = ???
  def createPGCanvas(): com.sun.javafx.sg.PGCanvas = ???
  def createPGCircle(): com.sun.javafx.sg.PGCircle = ???
  def createPGCubicCurve(): com.sun.javafx.sg.PGCubicCurve = ???
  def createPGEllipse(): com.sun.javafx.sg.PGEllipse = ???
  def createPGGroup(): com.sun.javafx.sg.PGGroup = ???
  def createPGImageView(): com.sun.javafx.sg.PGImageView = ???
  def createPGLine(): com.sun.javafx.sg.PGLine = ???
  def createPGMediaView(): com.sun.javafx.sg.PGMediaView = ???
  def createPGPath(): com.sun.javafx.sg.PGPath = ???
  def createPGPolygon(): com.sun.javafx.sg.PGPolygon = ???
  def createPGPolyline(): com.sun.javafx.sg.PGPolyline = ???
  def createPGQuadCurve(): com.sun.javafx.sg.PGQuadCurve = ???
  def createPGRegion(): com.sun.javafx.sg.PGRegion = ???
  def createPGSVGPath(): com.sun.javafx.sg.PGSVGPath = ???
  def createPGText(): com.sun.javafx.sg.PGText = ???

  def createParallelCamera(): com.sun.javafx.geom.ParallelCameraImpl = ???
  def createPerspectiveCamera(): com.sun.javafx.geom.PerspectiveCameraImpl = ???

  def createPerformanceTracker(): com.sun.javafx.perf.PerformanceTracker = ???
  def getMasterTimer(): com.sun.scenario.animation.AbstractMasterTimer = ???
  def getPerformanceTracker(): com.sun.javafx.perf.PerformanceTracker = ???

  def createPlatformImage(x$1: Int, x$2: Int): com.sun.javafx.tk.PlatformImage = ???
  def createSVGPath2D(x$1: javafx.scene.shape.SVGPath): com.sun.javafx.geom.Path2D = ???
  def createSVGPathObject(x$1: javafx.scene.shape.SVGPath): Object = ???
  def loadImage(x$1: java.io.InputStream, x$2: Int, x$3: Int, x$4: Boolean, x$5: Boolean): com.sun.javafx.tk.ImageLoader = ???
  def loadImage(x$1: String, x$2: Int, x$3: Int, x$4: Boolean, x$5: Boolean): com.sun.javafx.tk.ImageLoader = ???
  def loadImageAsync(x$1: com.sun.javafx.runtime.async.AsyncOperationListener[_ <: com.sun.javafx.tk.ImageLoader], x$2: String, x$3: Int, x$4: Int, x$5: Boolean, x$6: Boolean): com.sun.javafx.runtime.async.AsyncOperation = ???
  def loadPlatformImage(x$1: Any): com.sun.javafx.tk.ImageLoader = ???
  def renderToImage(x$1: com.sun.javafx.tk.Toolkit.ImageRenderingContext): Object = ???
  def toExternalImage(x$1: Any, x$2: Any): Object = ???
  def toFilterable(x$1: javafx.scene.image.Image): com.sun.scenario.effect.Filterable = ???

  def createStrokedShape(x$1: com.sun.javafx.geom.Shape, x$2: com.sun.javafx.sg.PGShape.StrokeType, x$3: Double, x$4: com.sun.javafx.sg.PGShape.StrokeLineCap, x$5: com.sun.javafx.sg.PGShape.StrokeLineJoin, x$6: Float, x$7: Array[Float], x$8: Float): com.sun.javafx.geom.Shape = ???
  def strokeContains(x$1: com.sun.javafx.geom.Shape, x$2: Double, x$3: Double, x$4: com.sun.javafx.sg.PGShape.StrokeType, x$5: Double, x$6: com.sun.javafx.sg.PGShape.StrokeLineCap, x$7: com.sun.javafx.sg.PGShape.StrokeLineJoin, x$8: Float): Boolean = ???

  def createTKEmbeddedStage(x$1: com.sun.javafx.embed.HostInterface): com.sun.javafx.tk.TKStage = ???
  def createTKPopupStage(x$1: javafx.stage.StageStyle, x$2: Any): com.sun.javafx.tk.TKStage = ???
  def createTKStage(style: javafx.stage.StageStyle): com.sun.javafx.tk.TKStage = ???

  def getBestCursorSize(x$1: Int, x$2: Int): javafx.geometry.Dimension2D = ???

  def getContextMap(): java.util.Map[Object, Object] = ???
  def getFilterContext(x$1: Any): com.sun.scenario.effect.FilterContext = ???

  def getFontLoader(): com.sun.javafx.tk.FontLoader = ???

  def getKeyCodeForChar(x$1: String): Int = ???

  def getMaximumCursorColors(): Int = ???

  def getMultiClickMaxX(): Int = ???
  def getMultiClickMaxY(): Int = ???
  def getMultiClickTime(): Long = ???

  def getNamedClipboard(x$1: String): com.sun.javafx.tk.TKClipboard = ???
  def getSystemClipboard(): com.sun.javafx.tk.TKClipboard = ???

  def getPlatformShortcutKey(): javafx.scene.input.KeyCode = ???

  def getPrimaryScreen(): Object = ???
  def getRefreshRate(): Int = ???
  def getScreens(): java.util.List[_] = ???
  def setScreenConfigurationListener(x$1: com.sun.javafx.tk.TKScreenConfigurationListener): com.sun.javafx.tk.ScreenConfigurationAccessor = ???

  def getSystemMenu(): com.sun.javafx.tk.TKSystemMenu = ???

  def imageContains(x$1: Any, x$2: Float, x$3: Float): Boolean = ???

  def installInputMethodRequests(x$1: com.sun.javafx.tk.TKScene, x$2: javafx.scene.input.InputMethodRequests): Unit = ???
  def isBackwardTraversalKey(x$1: javafx.scene.input.KeyEvent): Boolean = ???
  def isForwardTraversalKey(x$1: javafx.scene.input.KeyEvent): Boolean = ???

  def isExternalFormatSupported(x$1: Class[_]): Boolean = ???

  def registerDragGestureListener(x$1: com.sun.javafx.tk.TKScene, x$2: java.util.Set[javafx.scene.input.TransferMode], x$3: com.sun.javafx.tk.TKDragGestureListener): Unit = ???

  def requestNextPulse(): Unit = ???

  def setAnimationRunnable(x$1: com.sun.scenario.DelayedRunnable): Unit = ???

  def showDirectoryChooser(x$1: com.sun.javafx.tk.TKStage, x$2: String, x$3: java.io.File): java.io.File = ???
  def showFileChooser(x$1: com.sun.javafx.tk.TKStage, x$2: String, x$3: java.io.File, x$4: com.sun.javafx.tk.FileChooserType, x$5: java.util.List[javafx.stage.FileChooser.ExtensionFilter]): java.util.List[java.io.File] = ???

}

