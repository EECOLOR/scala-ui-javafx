package ee.ui.javafx.application

import ee.ui.application.Application
import ee.ui.application.ApplicationLauncher
import ee.ui.application.ApplicationDependencies
import ee.ui.javafx.nativeImplementation.NativeManager
import ee.ui.events.PulseEvent
import com.sun.javafx.tk.TKPulseListener
import ee.ui.application.ImplementationContract

trait JavaFxApplicationLauncher extends ApplicationLauncher {
  def applicationDependencies = new ApplicationDependencies {
    val implementationContract = new ImplementationContract {

      lazy val launcher = Launcher
      lazy val elementImplementationHandler = NativeManager
      lazy val pulseEvent = JavaFxPulseEvent
    }
  }
}

object JavaFxPulseEvent extends PulseEvent with TKPulseListener with Toolkit {

  toolkit addStageTkPulseListener this

  def pulse = fire
}