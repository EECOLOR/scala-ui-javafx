package ee.ui.javafx.application

import ee.ui.application.ImplicitDependencies
import ee.ui.application.Application
import ee.ui.application.ApplicationLauncher
import ee.ui.application.ApplicationDependencies
import ee.ui.javafx.nativeImplementation.NativeManagerDependencies

trait JavaFxApplicationLauncher extends ApplicationLauncher {
	def applicationDependencies = new ApplicationDependencies {
	  def launcher = Launcher
	  def application:() => Application = createApplication _
    
	  def nativeManagers = NativeManagerDependencies
	}
}