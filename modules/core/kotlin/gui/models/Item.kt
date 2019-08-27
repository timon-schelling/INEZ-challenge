@file:Suppress("unused")

package gui.models


import gui.validate.ItemValidator
import javafx.beans.property.*
import klang.Tool
import klang.check
import klang.suggestion.Suggestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tornadofx.*
import java.util.*

/**
 * a completable list item
 * @see MainModel
 * @param text
 * @param completed
 */
class Item(text: String, completed: Boolean = false) {

    val id = UUID.randomUUID()

    val textProperty = SimpleStringProperty(text)
    var text by textProperty

    val completedProperty = SimpleBooleanProperty(completed)
    var completed by completedProperty

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Item

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun validateAndSplit() = with(ItemValidator){
        if(this@Item.isValid()) ValidSplintedItem(this@Item.digit(), this@Item.text()) else null
    }
    data class ValidSplintedItem(val digit: Double, val text: String)
}


/**
 * @constructor
 * @param property containing [Item]
 */
class ItemModel(property: ObjectProperty<Item>) : ItemViewModel<Item>(itemProperty = property) {

    val textProperty = bind(autocommit = true) { item?.textProperty }
    var text by textProperty

    val completedProperty = bind(autocommit = true) { item?.completedProperty }
    var completed by completedProperty
}
