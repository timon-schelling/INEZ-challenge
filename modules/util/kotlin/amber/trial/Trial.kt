package amber.trial

class Trial<T>(private val block: () -> T) {
    private var error: Throwable? = null
    private val result: T? = run {
        try {
            return@run block()
        }
        catch (t: Throwable) {
            error = t
            return@run null
        }
    }

    infix fun alternat(block: (Throwable) -> T): T {
        return if (error != null) block(error!!) else result ?: block(NullPointerException())
    }

    infix fun catch(block: (Throwable) -> Unit): Trial<T> {
        if (error != null) block(error!!)
        return this
    }
}
