package gui.models

import amber.io.createFileIfNotExists
import amber.source.provider.FileContentSource
import gui.controllers.MainController
import javafx.beans.property.SimpleBooleanProperty
import klang.impl.*
import klang.impl.Unit
import klang.tool
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObjectSerializer
import org.languagetool.language.GermanyGerman
import tornadofx.*
import java.io.File

object MainModel {

    val dataSource = FileContentSource(
            File("data.json").apply {
                createFileIfNotExists()
                if (readText().isBlank()) {
                    writeText("{}")
                }
            }
    )

    val langTool = tool {
        val data = Json.parse(JsonObjectSerializer, File("settings.json").readText())
        +ProductRules(
                mutableListOf<ProductGroup>().apply {
                    data.getObject("products").forEach { k, v ->
                        add(ProductGroup(k, mutableListOf<Product>().apply{
                            v.jsonArray.forEach {
                                add(Product(it.primitive.content))
                            }
                        }))
                    }
                },
                mutableListOf<Unit>().apply {
                    data.getArray("units").forEach {
                        val unit = it.jsonObject
                        add(Unit(unit.getPrimitive("name").content, unit.getPrimitive("shortcut").content))
                    }
                }
        )
//        +AutoCompleteRule(File("Nomen.txt").readLines())
//        +JLanguageToolRule(GermanyGerman())
    }

    val isFilterCompletedElementsEnabledProperty = SimpleBooleanProperty(false)
    var isFilterCompletedElementsEnabled by isFilterCompletedElementsEnabledProperty

    val elements = SortedFilteredList<Item>().apply {
        predicate = { !(isFilterCompletedElementsEnabled && it.completed) }
        onChange {
            MainController.save()
        }
    }

}
