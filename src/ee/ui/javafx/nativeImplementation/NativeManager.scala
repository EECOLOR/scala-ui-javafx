package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.Group
import scala.collection.mutable
import ee.ui.nativeElements.{ Window => UiWindow }
import ee.ui.nativeElements.{ Stage => UiStage }
import ee.ui.nativeElements.{ Scene => UiScene }
import ee.ui.nativeElements.{ Text => UiText }
import ee.ui.nativeElements.{ Rectangle => UiRectangle }
import ee.ui.{ Node => UiNode }
import ee.ui.{ Group => UiGroup }
import ee.ui.javafx.Node

object NativeManager extends ee.ui.nativeImplementation.NativeManager {

  def apply(o: UiNode): Node =
    o match {
      case x: UiGroup => apply(x)
      case x: UiText => apply(x)
      case x: UiRectangle => apply(x)
      case _ => throw new Error("Unknown node type " + o)
    }

  def apply(o: UiWindow): Window = o match {
    case x: UiStage => apply(x)
  }

  def apply(o: UiStage): Stage = Stages.getOrElseUpdate(o, new Stage(o))
  def apply(o: UiScene): Scene = Scenes.getOrElseUpdate(o, new Scene(o))
  def apply(o: UiGroup): Group = Groups.getOrElseUpdate(o, new Group(o))
  def apply(o: UiText): Text = Texts.getOrElseUpdate(o, new Text(o))
  def apply(o: UiRectangle): Rectangle = Rectangles.getOrElseUpdate(o, new Rectangle(o))

  protected def register(o: UiStage): Unit = apply(o)

  protected def update(o: UiStage): Unit = apply(o).update
  protected def update(o: UiScene): Unit = apply(o).update
  protected def update(o: UiGroup): Unit = apply(o).update
  protected def update(o: UiText): Unit = apply(o).update
  protected def update(o: UiRectangle): Unit = apply(o).update

  object Stages extends mutable.WeakHashMap[UiStage, Stage]
  object Scenes extends mutable.WeakHashMap[UiScene, Scene]
  object Groups extends mutable.WeakHashMap[UiGroup, Group]
  object Texts extends mutable.WeakHashMap[UiText, Text]
  object Rectangles extends mutable.WeakHashMap[UiRectangle, Rectangle]
}


