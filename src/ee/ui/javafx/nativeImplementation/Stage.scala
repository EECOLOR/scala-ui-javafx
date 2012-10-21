package ee.ui.javafx.nativeImplementation

import com.sun.javafx.tk.TKStage
import ee.ui.nativeElements.StageStyle
import ee.ui.nativeElements.Modality
import javafx.stage.{ StageStyle => JavaFxStageStyle }
import javafx.stage.{ Modality => JavaFxModality }
import javafx.scene.image.{ Image => JavaFxImage }
import ee.ui.primitives.Image
import javafx.scene.image.{ Image => JavaFxImage }
import javafx.stage.{ Modality => JavaFxModality }
import javafx.stage.{ StageStyle => JavaFxStageStyle }
import scala.collection.JavaConversions._
import ee.ui.properties.PropertyChangeCollector
import ee.ui.properties.PropertyChangeCollector._
import ee.ui.properties.ReadOnlyProperty
import ee.ui.properties.Property

class Stage(override val implemented: ee.ui.nativeElements.Stage) extends Window(implemented) {

  val propertyChanges = new PropertyChangeCollector(
    implemented.resizable ~> (internalStage setResizable _),
    implemented.fullScreen ~> (internalStage setFullScreen _),
    implemented.iconified ~> (internalStage setIconified _),
    implemented.title ~> (internalStage setTitle _.orNull),

    implemented.minWidth ~> { n =>
      internalStage setMinimumSize (n.toInt, implemented.minHeight.toInt)
    },

    implemented.minHeight ~> { n =>
      internalStage setMinimumSize (implemented.minWidth.toInt, n.toInt)
    },

    implemented.maxWidth ~> { n =>
      internalStage setMaximumSize (n.toInt, implemented.maxHeight.toInt)
    },

    implemented.maxHeight ~> { n =>
      internalStage setMaximumSize (implemented.maxWidth.toInt, n.toInt)
    })

  override def update = {
	super.update
    propertyChanges.applyChanges
  }
  
  def closeWindow = implemented.hide
  
  protected def createInternalStage = {
    val window = implemented.owner

    val ownerStage = window map (NativeManager(_).internalStage) orNull

    val tkStage = toolkit createTKStage (
    	implemented.style, 
    	implemented.primary, 
    	implemented.modality, 
    	ownerStage)
    tkStage setImportant true

    // Finish initialization
    tkStage setResizable implemented.resizable
    tkStage setFullScreen implemented.fullScreen
    tkStage setIconified implemented.iconified
    tkStage setTitle implemented.title.orNull
    tkStage setMinimumSize (implemented.minWidth.toInt, implemented.minHeight.toInt)
    tkStage setMaximumSize (implemented.maxWidth.toInt, implemented.maxHeight.toInt)

    val javaFxIcons = implemented.icons map Converters.convertImage

    tkStage setIcons javaFxIcons

    println("Stage createInternalStage")
    
    tkStage
  }

  implicit private def style(style:Property[StageStyle]): JavaFxStageStyle = style.value match {
    case StageStyle.DECORATED => JavaFxStageStyle.DECORATED
    case StageStyle.TRANSPARENT => JavaFxStageStyle.TRANSPARENT
    case StageStyle.UNDECORATED => JavaFxStageStyle.UNDECORATED
    case StageStyle.UTILITY => JavaFxStageStyle.UTILITY
  }

  implicit private def modality(modality:Property[Modality]): JavaFxModality = modality.value match {
    case Modality.APPLICATION_MODAL => JavaFxModality.APPLICATION_MODAL
    case Modality.NONE => JavaFxModality.NONE
    case Modality.WINDOW_MODAL => JavaFxModality.WINDOW_MODAL
  }
}
