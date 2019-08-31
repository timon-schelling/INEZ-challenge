package amber.config

open class ConfigException(message: String, cause: Throwable? = null) : Exception(message, cause)

class IsNotConfiguredException(
        atPath: String,
        config: Config
) : ConfigException("There is no value at \"$atPath\" in config ($config)")
