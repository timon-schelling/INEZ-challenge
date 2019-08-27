package gui.custom

import gui.style.MainStylesheet
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import tornadofx.*

/**
 * creates A TornadoFX [Button] with a given [icon]
 * @param icon buttons icon
 * @param size buttons dimensions
 * @param color icon color
 * @param op TornadoFX dsl block used to configure the created [Button]
 */
fun EventTarget.iconButton(icon: Icons, size: Dimension<Dimension.LinearUnits> = 20.px, color: Color = Color.BLACK, op: Button.() -> Unit = {}) = iconButton(icon.path, size, color, op)

/**
 * creates A TornadoFX [Button] with a given [iconSVGPath]
 * @param iconSVGPath svg icon path used to draw icon
 * @param size buttons dimensions
 * @param color icon color
 * @param op TornadoFX dsl block used to configure the created [Button]
 */
fun EventTarget.iconButton(iconSVGPath: String, size: Dimension<Dimension.LinearUnits> = 20.px, color: Color = Color.BLACK, op: Button.() -> Unit = {}) = iconButton(
        Pane().apply {
            style {
                shape = iconSVGPath
                backgroundColor += color
                minWidth = size
                minHeight = size
                maxWidth = size
                maxHeight = size
            }
        }, size, op)

/**
 * creates A TornadoFX [Button] with a given [graphic]
 * @param graphic the as icon displayed [Node]
 * @param size buttons dimensions
 * @param op TornadoFX dsl block used to configure the created [Button]
 */
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
