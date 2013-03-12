package ee.ui.javafx.nativeImplementation

import ee.ui.events.NullEvent
import ee.ui.properties.Property
import ee.ui.observables.ObservableValue

trait NativeImplementation {
  val updateImplementation = new NullEvent

  implicit class ApplyOnUpdate[A](property: ObservableValue[A]) {

    var updateFunction: Option[A => Unit] = None
    var newValue: Option[A] = Some(property.value)

    def ~>(code: => A => Unit) = updateFunction = Some(code)

    updateImplementation {
      for {
        updateWith <- updateFunction
        value <- newValue
      } updateWith(value)

      newValue = None
    }

    property.change { value =>
      newValue = Some(value)
    }
  }

}