package ee.ui.display

import org.specs2.mutable.Specification
import utils.SignatureTest
import com.sun.javafx.tk.TKScene
import org.specs2.mock.Mockito
import ee.ui.implementation.contracts.SceneContract
import ee.ui.display.shapes.Rectangle
import ee.ui.implementation.DefaultContractHandlers
import test.toolkit.StubScene
import org.mockito.{ Mockito => MockitoLibrary }
import ee.ui.implementation.JavaFxNodeHandler
import ee.ui.display.shapes.JavaFxRectangle
import ee.ui.display.implementation.contracts.NodeContract
import ee.ui.display.implementation.contracts.RectangleContract

class JavaFxSceneTest extends Specification with Mockito {

  isolated
  xonly
  sequential

  val contractHandlers = new DefaultContractHandlers {
    override val nodes = new JavaFxNodeHandler {
      override val create: NodeContract => JavaFxNode = {
        case rectangle: RectangleContract => spy(new JavaFxRectangle(rectangle))
        case _ => throw new UnsupportedOperationException("unsupported")
      }
    }
  }
  val owner = new JavaFxWindowTest().javaFxWindow
  val javaFxScene: JavaFxScene = javaFxScene(new Scene)
  def javaFxScene(scene: SceneContract) = JavaFxScene(owner, scene)(contractHandlers = contractHandlers)

  "JavaFxScene" should {

    "have a TK representation" in {
      SignatureTest[JavaFxScene, TKScene](_.internalScene)
    }

    "call internalWindow.createTkScene" in {
      javaFxScene.internalScene must beAnInstanceOf[TKScene]
      there was one(owner.internalWindow).createTKScene(false)
    }

    "call internalScebe.setCamera" in {
      // the default camera will only be initialized if this method is called
      there was one(javaFxScene.internalScene).setCamera(null)
    }

    "call internalScene.setTKSceneListener" in {
      there was one(javaFxScene.internalScene).setTKSceneListener(any)
    }

    "call internalScene.setTKScenePaintListener" in {
      there was one(javaFxScene.internalScene).setTKScenePaintListener(any)
    }

    "reflect external size changes in the scene" in {
      val scene = new Scene
      val fxScene = javaFxScene(scene)
      var listener = fxScene.internalScene.asInstanceOf[StubScene].sceneListener

      listener.changedSize(1, 2)
      scene.width.value === 1
      scene.height.value === 2
    }

    "set dirty of root to false when the frame has rendered" in {
      val root = new Rectangle
      val scene = new Scene
      scene.root = root
      val javaFxRoot = contractHandlers.nodes(root)

      val fxScene = javaFxScene(scene)
      var listener = fxScene.internalScene.asInstanceOf[StubScene].paintListener

      listener.frameRendered()

      there was one(javaFxRoot).dirty_=(false)
    }

    "should call internalScene.markDirty when roots dirty property changes to true" in {
      val rectangle = new Rectangle
      val fxScene = javaFxScene(new Scene { root = rectangle })
      val fxRectangle = contractHandlers.nodes(rectangle)
      fxRectangle.dirty = false
      there was no(fxScene.internalScene).markDirty()
      fxRectangle.dirty = true
      there was one(fxScene.internalScene).markDirty()
    }

    "call internalScene.setRoot correctly" in {
      val rectangle = new Rectangle
      val scene1 = new Scene { root = None }
      val scene2 = new Scene { root = Some(rectangle) }
      val fxScene1 = javaFxScene(scene1)
      val fxScene2 = javaFxScene(scene2)
      val fxRectangle = contractHandlers.nodes(rectangle)
      there was no(fxScene1.internalScene).setRoot(any)
      there was one(fxScene2.internalScene).setRoot(fxRectangle.internalNode)
    }

    "handle root changes correctly" in {
      //also the removal of the change event of dirty
      todo
    }
  }
}