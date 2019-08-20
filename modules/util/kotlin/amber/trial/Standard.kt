package amber.trial

fun <T> trial(block: () -> T): Trial<T> {
    return Trial<T>(block)
}

fun tryAndForget(block: () -> Unit) {
    trial<Unit>(block)
}
