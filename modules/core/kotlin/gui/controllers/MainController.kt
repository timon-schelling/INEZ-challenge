package gui.controllers

import com.github.h0tk3y.regexDsl.regex
import gui.models.Item
import gui.models.MainModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObjectSerializer
import kotlinx.serialization.json.json
import tornadofx.*

/**
 * TornadoFX main controller
 */
object MainController : Controller() {

    /**
     * adds a item with text
     * @param text of the item to add
     */
    fun add(text: String) = add(Item(text))

    /**
     * adds a item
     * duplicates will be merged
     * @sample "1 Apple" & "2 Apple" -> "3 Apple"
     * @param item to add
     */
    fun add(item: Item) {
        val itemSplinted = item.validateAndSplit()
        if (itemSplinted != null) {
            MainModel.elements.forEach {
                val itItemSplinted = it.validateAndSplit()
                if (itItemSplinted != null) {
                    if (itItemSplinted.text == itemSplinted.text){
                        remove(it)
                        add("${
                            run {  
                                val digit = itItemSplinted.digit + itemSplinted.digit
                                if(digit - digit.toLong() == 0.0) digit.toLong() else digit
                            }.toString().replace(".", ",")
                        } ${itemSplinted.text}")
                        return@add
                    }
                }
            }
        }
        if (item.text.isNotBlank()) if(MainModel.elements.isEmpty()) MainModel.elements.add(item) else MainModel.elements.asReversed().add(item)
    }

    /**
     * removes a item
     * @param item
     */
    fun remove(item: Item) = MainModel.elements.remove(item)

    /**
     * changes complete of all items
     * @see Item.completed
     * @param completed
     */
    fun toggleCompleted(completed: Boolean) = with(MainModel.elements.items) {
        filter { it.completed != completed }.forEach { it.completed = completed }
        invalidate()
    }

    /**
     * loads model data from MainModel.dataSource
     * @see MainModel.dataSource
     */
    fun load() {
        val data = Json.parse(JsonObjectSerializer, MainModel.dataSource.read())
        MainModel.isFilterCompletedElementsEnabled = data.getPrimitiveOrNull("filterCompletedElements")?.booleanOrNull ?: false
        MainModel.elements.clear()
        MainModel.elements.apply {
            data.getArrayOrNull("entries")?.forEach {
                with(it.jsonObject) {
                    this@apply.add(Item(getPrimitive("text").content, getPrimitive("completed").boolean))
                }
            }
        }
    }

    /**
     * writes model data into MainModel.dataSource
     * @see MainModel.dataSource
     */
    fun save() {
        MainModel.dataSource.write(
                Json.stringify(JsonObjectSerializer, json {
                    "filterCompletedElements" to MainModel.isFilterCompletedElementsEnabled
                    "entries" to JsonArray(
                            MainModel.elements.items.map {
                                json {
                                    "completed" to it.completed
                                    "text" to it.text
                                }
                            }
                    )
                })
        )
    }

    init {
        MainModel.isFilterCompletedElementsEnabledProperty.onChange {
            MainModel.elements.refilter()
        }
    }
}
