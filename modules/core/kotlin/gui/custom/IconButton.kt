package gui.custom

import gui.Icons
import gui.style.MainStylesheet
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import tornadofx.*

fun EventTarget.iconButton(icon: Icons, size: Dimension<Dimension.LinearUnits> = 20.px, color: Color = Color.BLACK, op: Button.() -> Unit = {}) = iconButton(icon.path, size, color, op)

fun EventTarget.iconButton(path: String, size: Dimension<Dimension.LinearUnits> = 20.px, color: Color = Color.BLACK, op: Button.() -> Unit = {}) = iconButton(
        Pane().apply {
            style {
                shape = path
                backgroundColor += color
                minWidth = size
                minHeight = size
                maxWidth = size
                maxHeight = size
            }
        }, size, op)

fun EventTarget.iconButton(graphic: Node, size: Dimension<Dimension.LinearUnits> = 20.px, op: Button.() -> Unit = {}) = button {
    addClass(MainStylesheet.iconButton)
    style {
        minWidth = size
        minHeight = size
        maxWidth = size
        maxHeight = size
        prefWidth = size
        prefHeight = size
    }
    this.graphic = graphic
    op()
}
