package ee.ui.javafx.nativeImplementation
import ee.ui.primitives.Image
import javafx.scene.image.{ Image => JavaFxImage }

object Converters {
  def convertImage(image: Image): JavaFxImage = new JavaFxImage(image.url)
}