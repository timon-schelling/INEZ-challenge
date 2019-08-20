package amber.factory

interface FactoryWithParameter<P, T> {
    operator fun invoke(parameter: P): T
}
