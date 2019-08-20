package amber.pipeline

interface Phase<C : Context> {
    fun run(context: C)
}
