package amber.config.impl

import amber.config.AbstractConfig
import amber.config.Config
import amber.trial.trial

open class StackConfig(vararg impl: Config) : AbstractConfig() {

    private val impls = mutableListOf<Config>().apply {
        impl.reversed().forEach { add(it) }
    }

    fun load(config: Config) = impls.add(config)

    override fun _get(path: String): Any? {
        var result: Any? = null
        impls.reversed().first {
            trial {
                result = it.get(path)
                true
            } alternat {
                false
            }
        }
        return result
    }
}
