package gui.custom

import amber.collections.moveAllValues
import javafx.beans.property.*
import javafx.event.EventTarget
import javafx.geometry.Side
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import klang.apply
import klang.check
import klang.groupRepeatedSuggestions
import klang.rule.Rule
import klang.sortByRepetitions
import klang.suggestion.RepeatedSuggestion
import klang.suggestion.Suggestion
import kotlinx.coroutines.*
import tornadofx.*

class AutoCompleteTextField(val rule: Rule, val searchTimeout: Long = 3000L) : TextField() {

    val suggestionsProperty = SimpleObjectProperty<List<Suggestion>>()
    var suggestions by suggestionsProperty

    val popup = ContextMenu()

    private fun makeSuggestions(rule: Rule) {
        GlobalScope.launch(Dispatchers.Main) {
            val checkedText = text
            val currentCaretPosition = caretPosition
            val newSuggestions = async(Dispatchers.Default) {
                withTimeoutOrNull(timeMillis = searchTimeout) {
                    rule.check(text ?: "")
                }
            }.await()?.filter {
                currentCaretPosition > it.position.offset &&
                currentCaretPosition <= it.position.offset + it.position.length + 1 &&
                it.suggested.isNotBlank()
            }?.toMutableList()

            newSuggestions?.groupRepeatedSuggestions()
            newSuggestions?.sortByRepetitions()

            if(text == checkedText) suggestions = newSuggestions
        }
    }

    init {
        caretPositionProperty().onChange {
            makeSuggestions(rule)
        }
        suggestionsProperty.onChange {
            updatePopup()
            showPopup()
        }
        setOnMouseClicked {
            showPopup()
        }
        suggestions = mutableListOf<Suggestion>()
    }

    private fun updatePopup() {
        popup.items.clear()
        suggestions.forEach {
            popup.items.add(MenuItem(it.suggested).apply {
                action {
                    with(this@AutoCompleteTextField) {
                        text = text.apply(it)
                        this.positionCaret(it.position.offset + it.suggested.length)
                    }
                }
            })
        }
    }

    private fun showPopup() {
        popup.hide()
        popup.show(this, Side.BOTTOM, translateX, translateY)
    }
}

fun EventTarget.autoCompeteTextfield(rule: Rule, op: AutoCompleteTextField.() -> Unit = {}): AutoCompleteTextField {
    val node = AutoCompleteTextField(rule)
    node.attachTo(this, op)
    return node
}
