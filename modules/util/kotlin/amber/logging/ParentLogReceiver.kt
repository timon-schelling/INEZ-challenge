package amber.logging

import com.soywiz.klock.DateTime

class ParentLogReceiver(private val parent: Logger, val reuseTime: Boolean = false) : LogReceiver() {
    override fun onLog(logObject: LogObject) {
        parent.log(LogObject(parent, logObject.level, if (reuseTime) logObject.time else DateTime.now(), "", logObject))
    }
}
