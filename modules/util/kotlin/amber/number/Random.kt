package amber.number

fun IntRange.random() = (Math.random() * ((endInclusive + 1) - start) + start).toInt()
