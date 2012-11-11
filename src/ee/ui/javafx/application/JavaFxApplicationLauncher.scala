package ee.ui.javafx.application

import ee.ui.application.Application
import ee.ui.application.ApplicationLauncher
import ee.ui.application.ApplicationDependencies
import ee.ui.javafx.nativeImplementation.NativeManager
import ee.ui.events.PulseEvent
import com.sun.javafx.tk.TKPulseListener

trait JavaFxApplicationLauncher extends ApplicationLauncher {
	def applicationDependencies = new ApplicationDependencies {
	  lazy val launcher = Launcher
	  lazy val applicationConstructor = createApplication _
	  lazy val nativeManager = NativeManager
	  lazy val pulseEvent = JavaFxPulseEvent
	}
}

object JavaFxPulseEvent extends PulseEvent with TKPulseListener with Toolkit {
  
  toolkit addStageTkPulseListener this
  
  def pulse = fire
}