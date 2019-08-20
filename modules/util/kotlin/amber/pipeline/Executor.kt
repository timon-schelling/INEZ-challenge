package amber.pipeline

open class Executor<C : Context> {
    open fun execute(phases: List<Phase<C>>, context: C) {
        phases.forEach {
            it.run(context)
        }
    }
}
