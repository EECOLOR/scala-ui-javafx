package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.Group
import scala.collection.mutable
import ee.ui.display.{ Window => UiWindow }
import ee.ui.display.{ Scene => UiScene }
import ee.ui.display.{ Text => UiText }
import ee.ui.display.shape.{ Rectangle => UiRectangle }
import ee.ui.display.{ Node => UiNode }
import ee.ui.display.{ Group => UiGroup }
import ee.ui.javafx.Node
import ee.ui.display.implementation.WindowContract
import ee.ui.display.implementation.SceneContract
import ee.ui.display.implementation.DisplayImplementationHandler
import ee.ui.application.Application
import ee.ui.members.Event
import ee.ui.members.ReadOnlyEvent
import ee.ui.members.Property
import ee.ui.display.implementation.WindowImplementationHandler

class NativeManager(applicationCreated: ReadOnlyEvent[Application]) extends DisplayImplementationHandler {

  implicit val implicitSelf = this

  val application = Property[Option[Application]](None)
  application <== applicationCreated.once

  def apply(o: UiNode): Node =
    o match {
      case x: UiGroup => apply(x)
      case x: UiText => apply(x)
      case x: UiRectangle => apply(x)
      case _ => throw new Error("Unknown node type " + o)
    }

  def apply(o: WindowContract): Window = Windows.getOrElseUpdate(o, new Window(o))
  def apply(o: UiWindow): Window = apply(WindowContract(o))
  def apply(o: SceneContract): Scene = Scenes.getOrElseUpdate(o, new Scene(o))
  def apply(o: UiScene): Scene = apply(SceneContract(o))
  def apply(o: UiGroup): Group = Groups.getOrElseUpdate(o, new Group(o))
  def apply(o: UiText): Text = Texts.getOrElseUpdate(o, new Text(o))
  def apply(o: UiRectangle): Rectangle = Rectangles.getOrElseUpdate(o, new Rectangle(o))

  protected def show(o: WindowContract): Unit = apply(o).show()
  protected def hide(o: WindowContract): Unit = apply(o).hide()
  def close(window: ee.ui.display.Window) =
    application foreach { application =>
      Application.hide(application, window)
    }

  protected def update(o: WindowContract): Unit = apply(o).updateImplementation.fire
  protected def update(o: SceneContract): Unit = apply(o).updateImplementation.fire
  protected def update(o: UiGroup): Unit = apply(o).updateImplementation.fire
  protected def update(o: UiText): Unit = apply(o).updateImplementation.fire
  protected def update(o: UiRectangle): Unit = apply(o).updateImplementation.fire

  object Windows extends mutable.WeakHashMap[WindowContract, Window]
  object Scenes extends mutable.WeakHashMap[SceneContract, Scene]
  object Groups extends mutable.WeakHashMap[UiGroup, Group]
  object Texts extends mutable.WeakHashMap[UiText, Text]
  object Rectangles extends mutable.WeakHashMap[UiRectangle, Rectangle]
}


