package amber.config

import amber.trial.trial

interface Config {

    fun isFinal(): Boolean

    fun final()

    fun _get(path: String): Any?

    private fun checkNotFinal() {
        if (isFinal()) throw Exception("Final was called on this config. It is not editable.")
    }

    operator fun get(path: String) = trial { _get(path) } alternat { null }
            ?: throw IsNotConfiguredException(path, this)

    fun <T> value(path: String = "") = Delegate<T>(this, path)

    fun string(path: String = "") = value<String>(path)

    fun sub(path: String) = SubConfig(this, path)
}
