package amber.logging

import amber.event.Listener

abstract class LogReceiver : Listener<LogObject> {
    override fun LogObject.on() = onLog(this)
    abstract fun onLog(logObject: LogObject)
}
