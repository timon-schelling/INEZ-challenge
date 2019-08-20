package gui.views

import gui.Icons
import gui.style.MainStylesheet
import gui.controllers.MainController
import gui.custom.iconButton
import gui.models.Item
import gui.models.MainModel
import javafx.geometry.Pos
import tornadofx.*

class Footer() : View() {

    override val root = hbox {
        addClass(MainStylesheet.footer)
        iconButton(Icons.RESET, color = MainStylesheet.text) {
            action {
                MainController.toggleCompleted(false)
            }
        }
        checkbox("hide completed entry's", MainModel.isFilterCompletedElementsEnabledProperty){
            alignment = Pos.CENTER_LEFT
        }
        spacer {}
        label(stringBinding(integerBinding(MainModel.elements) { count<Item?> { !it!!.completed } }) { "$value item${value.plural} left" }) {

        }
    }

    val Int.plural: String get() = if (this == 1) "" else "s"
}
