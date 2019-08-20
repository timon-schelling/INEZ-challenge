package amber.pipeline

abstract class PipelineBase<C : Context>(phases: MutableList<Phase<C>>? = null, executor: Executor<C>? = null) :
        Phase<C> {

    protected val _phases = phases ?: ArrayList<Phase<C>>()
    val phases get() = _phases.toList()

    protected val executor: Executor<C> = executor ?: Executor<C>()

    fun execute(context: C): C {
        run(context)
        return context
    }
}
