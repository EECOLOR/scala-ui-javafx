package test.toolkit

import java.util.concurrent.Executors
import scala.concurrent.future
import scala.concurrent.JavaConversions.asExecutionContext

class ApplicationThread {
  protected implicit val context = asExecutionContext(Executors.newSingleThreadExecutor())

  def run(code: => Unit) = future(code)
}