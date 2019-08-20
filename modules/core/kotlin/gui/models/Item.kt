@file:Suppress("unused")

package gui.models


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

}

class ItemModel(property: ObjectProperty<Item>) : ItemViewModel<Item>(itemProperty = property) {

    val textProperty = bind(autocommit = true) { item?.textProperty }
    var text by textProperty

    val completedProperty = bind(autocommit = true) { item?.completedProperty }
    var completed by completedProperty

}
