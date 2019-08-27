package gui.views

import gui.custom.Icons
import gui.style.MainStylesheet
import gui.controllers.MainController
import gui.custom.iconButton
import gui.models.Item
import gui.models.ItemModel
import gui.models.MainModel
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import tornadofx.*

/**
 * a item list cell fragment representing a [Item]
 * @see ListCellFragment
 */
class ItemFragment() : ListCellFragment<Item>() {

    private val model = ItemModel(itemProperty)
    private lateinit var checkbox: CheckBox
    private lateinit var label: Label
    private lateinit var deleteButton: Button

    override val root = borderpane {
        addClass(MainStylesheet.itemRoot)
        center = hbox {
            spacing = 12.0
            editingProperty.value = false
            style {
                alignment = Pos.CENTER_LEFT
                padding = box(0.px, 0.px, 0.px, 0.5.px)
            }
            checkbox = checkbox(property = model.completedProperty) {
                action {
                    startEdit()
                    commitEdit(item)
                    MainModel.elements.refilter()
                    MainController.save()
                }
                style {
                    padding = box(0.px)
                    alignment = Pos.CENTER_LEFT
                }
            }
            label = label(model.textProperty) {
                setId(MainStylesheet.contentLabel)
                toggleClass(MainStylesheet.strikethrough, model.completedProperty)
                style {
                    padding = box(1.px)
                }
            }
        }
        right = hbox {
            deleteButton = iconButton(Icons.CLOSE, 17.px, MainStylesheet.red) {
                action { MainController.remove(item) }
                style {
                    padding = box(0.px, 0.3.em)
                    alignment = Pos.CENTER_RIGHT
                }
            }
            style {
                alignment = Pos.CENTER_LEFT
            }
        }
    }
}
