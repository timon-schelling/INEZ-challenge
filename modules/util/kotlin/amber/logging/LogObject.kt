package amber.logging

import amber.time.toTimeStamp
import com.soywiz.klock.DateTime

class LogObject(
        val logger: Logger,
        val level: LogLevel,
        val time: DateTime,
        var massage: String,
        val childs: List<LogObject>
) {
    constructor(logger: Logger, level: LogLevel, time: DateTime, massage: String, vararg childs: LogObject) : this(
            logger,
            level,
            time,
            massage,
            childs.toList()
    )

    fun format(block: LogObject.() -> String) = block()

    fun format(): String = format {
        "${logger.name} ${time.toTimeStamp()} ${level.name}: $massage" +
                if (childs.isNotEmpty()) {
                    buildString {
                        childs.forEach {
                            append("\n${it.format()}")
                        }
                    }.prependIndent()
                } else ""
    }
}
