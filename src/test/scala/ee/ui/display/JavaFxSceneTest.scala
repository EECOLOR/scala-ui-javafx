package ee.ui.display

import org.specs2.mutable.Specification
import utils.SignatureTest
import com.sun.javafx.tk.TKScene
import org.specs2.mock.Mockito
import ee.ui.implementation.contracts.SceneContract
import ee.ui.display.shapes.Rectangle
import ee.ui.implementation.DefaultContractHandlers

class JavaFxSceneTest extends Specification with Mockito {

  isolated
  xonly

  val contractHandlers = new DefaultContractHandlers
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
  }
}