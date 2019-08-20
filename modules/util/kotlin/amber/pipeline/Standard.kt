package amber.pipeline

class LambdaPhase<C : Context>(private val block: C.() -> Unit) : Phase<C> {
    override fun run(context: C) {
        context.block()
    }
}

fun <C : Context> phase(block: C.() -> Unit): Phase<C> {
    return LambdaPhase<C>(block)
}

fun <C : Context> Pipeline<C>.addAll(vararg phase: Phase<C>) {
    phase.forEach { add(it) }
}

operator fun <C : Context> Pipeline<C>.invoke(block: C.() -> Unit) = intercept(block)

fun <C : Context> Pipeline<C>.intercept(block: C.() -> Unit) = interceptAfter(block)

fun <C : Context> Pipeline<C>.interceptAfter(block: C.() -> Unit): Phase<C> {
    val phase = phase<C>(block)
    add(phase)
    return phase
}

fun <C : Context> Pipeline<C>.interceptBefore(block: C.() -> Unit): Phase<C> {
    val phase = phase<C>(block)
    push(phase)
    return phase
}

fun <C : Context> Pipeline<C>.interceptAfter(after: Phase<C>, block: C.() -> Unit): Phase<C> {
    val phase = phase<C>(block)
    addAfter(after, phase)
    return phase
}

fun <C : Context> Pipeline<C>.interceptBefore(before: Phase<C>, block: C.() -> Unit): Phase<C> {
    val phase = phase<C>(block)
    addBefore(before, phase)
    return phase
}

class EmptyInput : Input

fun emptyInput() = EmptyInput()

class EmptyResult : Result

fun emptyResult() = EmptyResult()
