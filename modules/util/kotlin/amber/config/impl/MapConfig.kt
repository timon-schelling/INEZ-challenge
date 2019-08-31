package amber.config.impl

import amber.config.AbstractConfig

open class MapConfig(private val map: MutableMap<String, Any> = mutableMapOf()) : AbstractConfig() {

    override fun _get(path: String) = map[path]

}
