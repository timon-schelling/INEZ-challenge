package amber.logging.format

import amber.logging.LogObject

class LambdaFormatter(private val block: LogObject.() -> Unit) : Formatter {
    override fun LogObject.format() = block()
}
