package amber.event

open class NotFireableEvent<T>(
        protected val listenerList: ListenerList<T> = ListenerList(),
        protected val executor: Executor<T> = Executor()
) {

    operator fun invoke(block: T.() -> Unit) {
        this(LambdaListener(block))
    }

    operator fun invoke(listener: Listener<T>) {
        listenerList.add(listener)
    }

    fun add(listener: Listener<T>) = listenerList.add(listener)

    fun remove(listener: Listener<T>) = listenerList.remove(listener)

    protected fun fireProtected(data: T) {
        executor.execute(data, listenerList.toList())
    }
}
