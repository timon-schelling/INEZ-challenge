package amber.config

class SubConfig(private val delegate: Config, private val prefix: String) : AbstractConfig() {
    override fun _get(path: String) = delegate.get("$prefix.$path")
}