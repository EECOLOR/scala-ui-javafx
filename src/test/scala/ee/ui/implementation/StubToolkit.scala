package ee.ui.implementation

import test.toolkit.{StubToolkit => JavaFxStubToolkit}
import org.specs2.specification.Example
import org.specs2.execute.AsResult
import org.specs2.mutable.Specification
import org.mockito.{ Mockito => MockitoLibrary }

trait StubToolkit extends Toolkit { self:Specification =>
  def stubToolkit = toolkit.asInstanceOf[JavaFxStubToolkit]
  def stubToolkitMock = stubToolkit.shadowMock
  
  implicit class MockitoExample(s: String) {
    def resetToolkitMock[T: AsResult](r: => T): Example = {
      MockitoLibrary reset stubToolkit.shadowMock
      exampleFactory.newExample(s, r)
    }
  }
}