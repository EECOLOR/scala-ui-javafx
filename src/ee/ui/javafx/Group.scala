package ee.ui.javafx

import ee.ui.javafx.application.Toolkit
import ee.ui.properties.Add
import ee.ui.properties.Remove
import ee.ui.properties.Clear
import ee.ui.javafx.nativeImplementation.NativeManager

class Group(val implemented: ee.ui.Group) extends Node with Toolkit {

  val internalNode = toolkit.createPGGroup

  private var firstIndex = 0

  private def updateFirstIndex(index: Int) =
    if (index < firstIndex) firstIndex = index

  implemented.children.onChangedIn {
    case Add(index, _) => updateFirstIndex(index)
    case Remove(index, _) => updateFirstIndex(index)
    case x: Clear[_] => updateFirstIndex(0)
  }

  def update = {
    val children = implemented.children

    internalNode clearFrom firstIndex

    for (i <- firstIndex until children.size) {
      val node = children(i)
      println("Group adding node " + node)
      internalNode add (i, NativeManager(node).internalNode)
    }

    val removed = children.removed

    if (removed.size > Group.REMOVED_CHILDREN_THRESHOLD)
      internalNode markDirty
    else
      removed foreach {
        case Remove(_, node) =>
          internalNode addToRemoved (NativeManager(node).internalNode)
      }

    firstIndex = children.size
    
    children.reset
  }
}

object Group {
  val REMOVED_CHILDREN_THRESHOLD = 20
}