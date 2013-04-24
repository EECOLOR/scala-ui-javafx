package ee.ui.implementation

import utils.SubtypeTest
import org.specs2.mutable.Specification

object JavaFxTextHelperTest extends Specification {
  
  xonly
  
  "JavaFxTextHelper" should {
    
    "extend TextHelper" in {
      SubtypeTest[JavaFxTextHelper <:< TextHelper]
    }
    
  }
  
}