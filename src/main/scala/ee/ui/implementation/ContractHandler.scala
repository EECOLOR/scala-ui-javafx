package ee.ui.implementation

import scala.collection.mutable

trait ContractHandler[ContractType, ImplementationType] {

  val create: ContractType => ImplementationType

  lazy val contracts = mutable.Map.empty[ContractType, ImplementationType].withDefault(create)

  def apply(contract: ContractType): ImplementationType = 
    contracts.getOrElseUpdate(contract, contracts(contract))

}