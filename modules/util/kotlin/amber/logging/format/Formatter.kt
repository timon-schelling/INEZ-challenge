package amber.logging.format

import amber.logging.LogObject

interface Formatter {
    operator fun invoke(logObject: LogObject) = logObject.format()
    fun LogObject.format()
}
