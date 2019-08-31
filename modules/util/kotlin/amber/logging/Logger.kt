package amber.logging

import amber.event.Event
import amber.logging.util.stackTraceAsSting
import com.soywiz.klock.DateTime

open class Logger(val name: String, parent: Logger? = null) {

    private val _onLog = Event<LogObject>()
    val onLog = _onLog.toSaveEvent()

    init {
        if (parent != null)
            addParent(parent)
    }

    fun addParent(parent: Logger) = addReceiver(ParentLogReceiver(parent, true))

    fun removeReceiver(r: LogReceiver) {
        onLog.remove(r)
    }

    fun addReceiver(r: LogReceiver) {
        onLog.add(r)
    }

    fun log(level: LogLevel, message: String) {
        log(LogObject(this, level, DateTime.now(), message))
    }

    fun log(logObject: LogObject) {
        if (logObject.logger === this)
            _onLog.fire(logObject)
    }

    operator fun invoke(level: LogLevel, message: String) = log(level, message)
    fun trace(message: String) = log(LogLevel.TRACE, message)
    fun debug(message: String) = log(LogLevel.DEBUG, message)
    fun info(message: String) = log(LogLevel.INFO, message)
    fun warn(message: String) = log(LogLevel.WARN, message)
    fun error(message: String) = log(LogLevel.ERROR, message)
    fun exception(e: Throwable) = error(e.stackTraceAsSting())
    fun error(e: Throwable) = exception(e)
}
