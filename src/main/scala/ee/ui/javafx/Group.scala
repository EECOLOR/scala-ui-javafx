package ee.ui.javafx

import ee.ui.javafx.application.Toolkit
import ee.ui.members.details.Add
import ee.ui.members.details.Remove
import ee.ui.members.details.Clear
import ee.ui.javafx.nativeImplementation.NativeManager
import scala.collection.mutable.ListBuffer

class Group(override val implemented: ee.ui.display.Group) extends Node(implemented) with Toolkit {

  val internalNode = toolkit.createPGGroup

  private var firstIndex = 0

  private def updateFirstIndex(index: Int) =
    if (index < firstIndex) firstIndex = index

  val removed = ListBuffer[Remove[ee.ui.display.Node]]()
    
  implemented.children.change collect {
    case Add(index, _) => updateFirstIndex(index)
    case x @ Remove(index, _) => {
      //remember it was removed
      removed += x
      updateFirstIndex(index)
    }
    case x @ Clear(elements) => {
      removed ++= elements.view.zipWithIndex.map {
        case (elem, index) => Remove(index, elem)
      }
      updateFirstIndex(0)
    }
  }

  updateImplementation {
    
    val children = implemented.children

    internalNode clearFrom firstIndex

    for (i <- firstIndex until children.size) {
      val node = children(i)
      println("Group adding node " + node)
      internalNode add (i, NativeManager(node).internalNode)
    }

    if (removed.size > Group.REMOVED_CHILDREN_THRESHOLD)
      internalNode.markDirty()
    else
      removed foreach {
        case Remove(_, node) =>
          internalNode addToRemoved (NativeManager(node).internalNode)
      }

    firstIndex = children.size
    
    removed.clear
    
    //TODO for testing
    internalNode.markDirty
  }
}

object Group {
  val REMOVED_CHILDREN_THRESHOLD = 20
}