package gui.controllers

import gui.models.Item
import gui.models.MainModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObjectSerializer
import kotlinx.serialization.json.json
import tornadofx.*

object MainController : Controller() {

    fun add(text: String) {
        if (text.isNotBlank()) MainModel.elements.add(Item(text))
    }

    fun remove(todo: Item) = MainModel.elements.remove(todo)

    fun toggleCompleted(completed: Boolean) = with(MainModel.elements) {
        val oldValue = MainModel.isFilterCompletedElementsEnabled
        MainModel.isFilterCompletedElementsEnabled = false
        filter { it.completed != completed }.forEach { it.completed = completed }
        invalidate()
        MainModel.isFilterCompletedElementsEnabled = oldValue
    }

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
