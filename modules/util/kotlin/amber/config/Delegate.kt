package amber.config

import amber.trial.trial
import kotlin.reflect.KProperty

class Delegate<T>(private val config: Config, private val path: String = "") {

    operator fun getValue(thisRef: Any?, property: KProperty<*>) = trial { config.get((if (path.isBlank()) property.name else path)) as T } alternat { throw IsNotConfiguredException(path, config) }

}