package ee.ui.javafx.nativeImplementation

import ee.ui.javafx.Group
import scala.collection.mutable
import ee.ui.nativeElements.{Window => UiWindow}
import ee.ui.nativeElements.{Stage => UiStage}
import ee.ui.nativeElements.{Scene => UiScene}
import ee.ui.{Node => UiNode}
import ee.ui.{Group => UiGroup}
import ee.ui.javafx.Node

object NativeManager extends ee.ui.nativeImplementation.NativeManager {

  def update(o:UiStage):Unit = Stages.getOrElseUpdate(o, new Stage(o)).update
  def update(o:UiScene):Unit = Scenes.getOrElseUpdate(o, new Scene(o)).update
  def update(o:UiGroup):Unit = Groups.getOrElseUpdate(o, new Group(o)).update
}

object Nodes {
  def apply(o: UiNode): Node = o match {
    case x: UiGroup => Groups(x)
  }
}
object Windows {
  def apply(o:UiWindow):Window = o match {
    case x:UiStage => Stages(x)
  }
}

object Stages extends mutable.HashMap[UiStage, Stage]
object Scenes extends mutable.HashMap[UiScene, Scene]
object Groups extends mutable.HashMap[UiGroup, Group]

