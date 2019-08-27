package gui.custom

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
import klang.suggestion.Suggestion
import kotlinx.coroutines.*
import tornadofx.*

/**
 * a TornadoFX textfield that can suggest user input by a given [rule]
 * @property rule used to suggest user input
 * @property searchTimeout the timeout for a suggestion search
 */
class AutoCompleteTextField(val rule: Rule, val searchTimeout: Long = 3000L) : TextField() {

    private val suggestionsProperty = SimpleObjectProperty<List<Suggestion>>()
    private var suggestions by suggestionsProperty

    /**
     * the [popup] used for user input suggestions
     */
    private val popup = ContextMenu()

    /**
     * suggests user input by a given [rule]
     * @param rule
     */
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
                currentCaretPosition <= it.position.offset + it.position.length &&
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

    /**
     * updates suggestion [popup]
     */
    private fun updatePopup() {
        popup.items.clear()
        suggestions.forEach {
            popup.items.add(MenuItem(it.message).apply {
                action {
                    with(this@AutoCompleteTextField) {
                        text = text.apply(it)
                        this.positionCaret(it.position.offset + it.suggested.length)
                    }
                }
            })
        }
    }

    /**
     * shows suggestion [popup]
     */
    private fun showPopup() {
        popup.hide()
        popup.show(this, Side.BOTTOM, translateX, translateY)
    }
}

/**
 * creates a [AutoCompleteTextField] with a given [rule]
 * @param rule used to suggest user input
 * @param op TornadoFX dsl block used to configure the created [AutoCompleteTextField]
 * @return the created [AutoCompleteTextField]
 */
fun EventTarget.autoCompeteTextfield(rule: Rule, op: AutoCompleteTextField.() -> Unit = {}): AutoCompleteTextField {
    val node = AutoCompleteTextField(rule)
    node.attachTo(this, op)
    return node
}
