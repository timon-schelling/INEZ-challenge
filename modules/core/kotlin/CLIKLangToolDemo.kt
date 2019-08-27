import gui.models.Product
import gui.models.ProductGroup
import gui.models.Unit
import klang.apply
import klang.check
import klang.impl.*
import klang.tool
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObjectSerializer
import org.languagetool.language.GermanyGerman
import java.io.File

fun main() {
    val tool = tool {
        val data = Json.parse(JsonObjectSerializer, File("data.json").readText())
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
        +JLanguageToolRule(GermanyGerman())
    }

    while (true) {
        var line = readLine()!!

        if(line == "/e"){
            break
        }
        var suggestions = tool.check(line)
        while (suggestions.isNotEmpty()) {

            println(line)
            println()

            suggestions.forEachIndexed { i, it ->
                println("$i: ${it.original} --> ${it.suggested}")
            }

            val command = readLine()!!

            println()



            if(command == "/e"){
                println(line)
                break
            } else {
                val use = suggestions[command.toInt()]
                line = line.apply(use)
            }

            suggestions = tool.check(line)

            println(line)
            println()
            println()
            println()
        }
    }
}
