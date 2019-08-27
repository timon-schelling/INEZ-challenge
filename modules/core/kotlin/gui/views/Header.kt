package gui.views

import gui.custom.Icons
import gui.style.MainStylesheet
import gui.controllers.MainController
import gui.custom.autoCompeteTextfield
import gui.custom.iconButton
import gui.models.MainModel
import javafx.geometry.Pos
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tornadofx.*

/**
 * TornadoFX header view
 */
class Header() : View() {

    private lateinit var addTextField: TextField

    override val root = vbox {
        addClass(MainStylesheet.header)
        style {
            alignment = Pos.CENTER_LEFT
            padding = box(0.px, 0.px, 0.px, 2.6.px)
        }
        hbox {
            addClass(MainStylesheet.addItemRoot)
            iconButton(Icons.PLUS, 1.2.em, MainStylesheet.text) {
                action {
                    MainController.add(addTextField.text)
                    addTextField.clear()
                }
            }
            autoCompeteTextfield(MainModel.langTool) {
                GlobalScope.launch {
                    withContext(Dispatchers.Main) {
                        requestFocus()
                    }
                }
                addTextField = this
                promptText = ""
                useMaxWidth = true
                hgrow = Priority.ALWAYS
                action {
                    MainController.add(text)
                    clear()
                }
            }
        }
    }
}
