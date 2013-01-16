package ee.ui.javafx.application

import ee.ui.application.Application
import scala.collection.mutable
import javafx.application.Application.{Parameters => JavaFxParameters}

object Parameters {
  
    val params = mutable.Map[Application, JavaFxParameters]()
    
    def getParameters(a:Application):JavaFxParameters = params(a)

    def registerParameters(a:Application, p:JavaFxParameters) = params += a -> p
}