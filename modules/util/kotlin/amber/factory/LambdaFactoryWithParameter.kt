package amber.factory

class LambdaFactoryWithParameter<P, T>(private val block: (P) -> T) : FactoryWithParameter<P, T> {
    override fun invoke(parameter: P) = block(parameter)
}
