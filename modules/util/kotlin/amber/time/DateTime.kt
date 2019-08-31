package amber.time

import amber.text.replace
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import com.soywiz.klock.parse

val DEFAULT_DATE_TIME_FORMAT by lazy { DateFormat("dd.MM.yyyy-HH:mm:ss:SSS") }

fun DateTime.toTimeStamp(): String {
    return "[${format(DEFAULT_DATE_TIME_FORMAT)}]"
}

fun String.toDateTime(): DateTime {
    return DEFAULT_DATE_TIME_FORMAT.parse(this).utc
}

fun String.timeStampToDateTime(): DateTime {
    return DEFAULT_DATE_TIME_FORMAT.parse(replace(listOf("[", "]"), "")).utc
}


fun DateTime.formatDefault() = format(DEFAULT_DATE_TIME_FORMAT)
