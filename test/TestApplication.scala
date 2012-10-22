import ee.ui.application.Application
import ee.ui.nativeElements.Stage
import ee.ui.javafx.application.JavaFxApplicationLauncher

class TestApplication extends Application {
    def start(stage:Stage) = {
        
    }
}

object TestApplication extends JavaFxApplicationLauncher {
	def createApplication = new TestApplication
}