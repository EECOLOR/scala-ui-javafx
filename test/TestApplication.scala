import ee.ui.application.Application
import ee.ui.nativeElements.Window
import ee.ui.javafx.application.JavaFxApplicationLauncher

class TestApplication extends Application {
    def start(window:Window) = {
        
    }
}

object TestApplication extends JavaFxApplicationLauncher {
	def createApplication = new TestApplication
}