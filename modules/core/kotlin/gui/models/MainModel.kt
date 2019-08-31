package gui.models

import amber.io.createFileIfNotExists
import amber.source.provider.FileContentSource
import amber.source.provider.LambdaSource
import gui.controllers.MainController
import javafx.beans.property.SimpleBooleanProperty
import klang.Tool
import klang.impl.*
import klang.tool
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObjectSerializer
import tornadofx.*
import java.io.File

/**
 * TornadoFX main model
 */
object MainModel {

    /**
     * container for save file
     */
    val dataSource by lazy{
        FileContentSource(
                File("data.json").apply {
                    createFileIfNotExists()
                    if (readText().isBlank()) {
                        writeText("{}")
                    }
                }
        )
    }

    /**
     * container for settings file
     */
    private val settingFileSource = FileContentSource(File("settings.json"))

    /**
     * container for settings
     */
    private val settingSource = LambdaSource {
        val data = Json.parse(JsonObjectSerializer, settingFileSource.read())
        Settings().apply {
            productGroups = mutableListOf<ProductGroup>().apply {
                data.getObject("products").forEach { k, v ->
                    add(ProductGroup(k, mutableListOf<Product>().apply{
                        v.jsonArray.forEach {
                            add(Product(it.primitive.content.replace("%PRODUCT%", k)))
                        }
                    }))
                }
            }
            units = mutableListOf<Unit>().apply {
                data.getArray("units").forEach {
                    val unit = it.jsonObject
                    add(Unit(unit.getPrimitive("name").content, unit.getPrimitive("shortcut").content))
                }
            }
        }
    }

    /**
     * settings
     * @see lazy
     */
    val settings by lazy {
        settingSource.read()
    }

    /**
     * main [Tool] used to suggest user input
     */
    val langTool: Tool = tool {
        +ProductRules(settings.productGroups, settings.units)
    }

    val isFilterCompletedElementsEnabledProperty = SimpleBooleanProperty(false)
    var isFilterCompletedElementsEnabled by isFilterCompletedElementsEnabledProperty

    /**
     * sorted and filtered list of [Item]
     */
    val elements = SortedFilteredList<Item>().apply {
        predicate = { !(isFilterCompletedElementsEnabled && it.completed) }
        onChange {
            MainController.save()
        }
    }
}
