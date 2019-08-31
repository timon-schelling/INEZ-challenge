package amber.logging.receiver

import amber.logging.LogLevel
import amber.logging.LogObject
import amber.logging.LogReceiver

class SimpleConsoleLogReceiver(val level: LogLevel = LogLevel.TRACE) : LogReceiver() {
    override fun onLog(logObject: LogObject) {
        if (logObject.level.isMoreOrTheSameImportant(level)) {
            var logString = logObject.format()
            if (logString.endsWith("\n")) logString = logString.substringBeforeLast("\n")
            println(logString)
        }
    }
}
