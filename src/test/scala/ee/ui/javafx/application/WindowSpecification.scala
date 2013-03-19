package ee.ui.javafx.application

import org.specs2.Specification
import ee.ui.display.implementation.WindowContract
import ee.ui.javafx.nativeImplementation.Window
import ee.ui.javafx.nativeImplementation.NativeManager
import ee.ui.members.Event
import ee.ui.application.Application
import com.sun.javafx.tk.TKStageListener
import com.sun.javafx.tk.FocusCause
import com.sun.javafx.pgstub.StubStage
import ee.ui.display.{ Window => UiWindow }
import ee.ui.display.WindowStyle
import ee.ui.display.Modality
import org.specs2.mock._
import ee.ui.primitives.Image
import com.sun.javafx.tk.{Toolkit => JavaFxToolkit}
import javafx.stage.{ Modality => JavaFxModality }
import javafx.stage.StageStyle
import javafx.scene.image.{Image => JavaFxImage}

object WindowSpecification extends Specification with Mockito {
  
  def createJavaFxWindow(window: UiWindow, nativeManager:NativeManager = new NativeManager(Event[Application])) = {
    val test = ""
    new Window(WindowContract(window))(nativeManager)
  }

  def is = "Window specification".title ^
    br ^ "Showing a window" ! {

      val window = new UiWindow
      val javaFxWindow = createJavaFxWindow(window)

      val internalStage = javaFxWindow.internalStage.asInstanceOf[StubStage]

      javaFxWindow.show()

      (16d ==== window.x) and
        (12d ==== window.y) and
        (256d ==== window.width) and
        (192d ==== window.height) and
        (internalStage.visible === true) and
        (internalStage.opacity === 1)

    } ^ "Internal stage creation" ! {

      val nativeManager = new NativeManager(Event[Application])
      val windowOwner = new UiWindow(primary = true)
      val javaFxOwner = createJavaFxWindow(windowOwner, nativeManager)
BreakPoint.set
      val window = new UiWindow(defaultStyle = WindowStyle.UTILITY) {
        x = 13
        y = 14
        width = 255
        height = 191
        opacity = 0.8
        modality = Modality.APPLICATION_MODAL
        owner = windowOwner
        resizable = false
        fullScreen = true
        iconified = true
        title = "test"
        minWidth = 13
        minHeight = 14
        maxWidth = 15
        maxHeight = 16
        icons ++= Seq(new Image { val url = "http://test" })
      }
      val toolkitSpy = spy(JavaFxToolkit.getToolkit())
      
      val javaFxWindow = new Window(WindowContract(window))(nativeManager) {
        override val toolkit = toolkitSpy
      }
      
      val stageSpy = spy(javaFxWindow.internalStage)

      (there was one(toolkitSpy).createTKStage(StageStyle.UTILITY, false, JavaFxModality.APPLICATION_MODAL, javaFxOwner.internalStage)) and
      (there was one(toolkitSpy).createTKStage(StageStyle.DECORATED, true, JavaFxModality.NONE, null)) and
      (there was one(stageSpy).setImportant(true)) and
      (there was one(stageSpy).setResizable(false)) and
      (there was one(stageSpy).setFullScreen(true)) and
      (there was one(stageSpy).setIconified(true)) and
      (there was one(stageSpy).setTitle("test")) and
      (there was one(stageSpy).setMinimumSize(13, 14)) and
      (there was one(stageSpy).setMaximumSize(15, 16)) and
      (there was one(stageSpy).setIcons(any[java.util.List[JavaFxImage]]))
    } ^
    end

}