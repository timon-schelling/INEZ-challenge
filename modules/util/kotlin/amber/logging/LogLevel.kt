package amber.logging

enum class LogLevel {
    TRACE {
        override val value = 4
    },
    DEBUG {
        override val value = 3
    },
    INFO {
        override val value = 2
    },
    WARN {
        override val value = 1
    },
    ERROR {
        override val value = 0
    };

    abstract val value: Int
    fun isMoreOrTheSameImportant(than: LogLevel): Boolean {
        if (value <= than.value) return true
        return false
    }
}
