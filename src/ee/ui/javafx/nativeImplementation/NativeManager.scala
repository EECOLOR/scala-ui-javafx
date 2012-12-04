package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.Group
import scala.collection.mutable
import ee.ui.nativeElements.{ Window => UiWindow }
import ee.ui.nativeElements.{ Scene => UiScene }
import ee.ui.nativeElements.{ Text => UiText }
import ee.ui.nativeElements.{ Rectangle => UiRectangle }
import ee.ui.{ Node => UiNode }
import ee.ui.{ Group => UiGroup }
import ee.ui.javafx.Node
import ee.ui.nativeImplementation.WindowContract
import ee.ui.nativeImplementation.SceneContract

object NativeManager extends ee.ui.nativeImplementation.ElementImplementationHandler {

  def apply(o: UiNode): Node =
    o match {
      case x: UiGroup => apply(x)
      case x: UiText => apply(x)
      case x: UiRectangle => apply(x)
      case _ => throw new Error("Unknown node type " + o)
    }

  def apply(o: WindowContract): Window = Windows.getOrElseUpdate(o, new Window(o))
  def apply(o: UiWindow):Window = apply(WindowContract(o))
  def apply(o: SceneContract): Scene = Scenes.getOrElseUpdate(o, new Scene(o))
  def apply(o: UiScene):Scene = apply(SceneContract(o))
  def apply(o: UiGroup): Group = Groups.getOrElseUpdate(o, new Group(o))
  def apply(o: UiText): Text = Texts.getOrElseUpdate(o, new Text(o))
  def apply(o: UiRectangle): Rectangle = Rectangles.getOrElseUpdate(o, new Rectangle(o))

  protected def register(o: WindowContract): Unit = apply(o)

  protected def update(o: WindowContract): Unit = apply(o).update
  protected def update(o: SceneContract): Unit = apply(o).update
  protected def update(o: UiGroup): Unit = apply(o).update
  protected def update(o: UiText): Unit = apply(o).update
  protected def update(o: UiRectangle): Unit = apply(o).update

  object Windows extends mutable.WeakHashMap[WindowContract, Window]
  object Scenes extends mutable.WeakHashMap[SceneContract, Scene]
  object Groups extends mutable.WeakHashMap[UiGroup, Group]
  object Texts extends mutable.WeakHashMap[UiText, Text]
  object Rectangles extends mutable.WeakHashMap[UiRectangle, Rectangle]
}


