package gui.style

import javafx.scene.paint.Color
import tornadofx.*

internal operator fun <T> MultiValue<T>.minusAssign(value: T): Unit = elements.run {
    clear()
    add(value)
}

internal fun Color.brightness(factor: Double) = deriveColor(0.0, saturation, 1 / factor, opacity)

