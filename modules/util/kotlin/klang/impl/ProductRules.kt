package klang.impl

import klang.input.Input
import klang.rule
import klang.rule.Rule
import klang.rule.RuleGroup
import klang.suggestion.Position
import klang.suggestion.SimpleSuggestion
import klang.suggestion.Suggestion
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

data class Unit(val name: String, val shortcut: String)

data class Product(val name: String)

data class ProductGroup(val name: String, val products: MutableList<Product>)

class ProductRules(private val products: List<ProductGroup>, private val units: List<Unit>) : RuleGroup() {
    init {
        add(UnitsRule(units))
        add(SynonymReplaceRule(mutableListOf<Synonym>().apply {
            units.forEach {
                add(Synonym(it.shortcut, it.name))
            }
        }))
        add(AutoCompleteRule(mutableListOf<String>().apply {
            units.forEach {
                add(it.name)
            }
            products.forEach {
                add(it.name)
            }
        }))
        add(ReplaceWithActualProductNameRule(products))
    }

    class ReplaceWithActualProductNameRule(private val products: List<ProductGroup>) : Rule() {

        override fun invoke(input: Input): List<Suggestion> = rule(input) {
            products.forEach { group ->
                group.products.forEach {
                    var index = 0
                    while (index < input.text.length) {
                        val positionOfProductString = input.text.indexOf(group.name, index)
                        index = if (positionOfProductString >= 0) {
                            suggest(ReplaceWithActualProductNameSuggestion(group.name, it.name, Position(index, group.name.length), this@ReplaceWithActualProductNameRule, it))
                            positionOfProductString + 1
                        } else input.text.length
                    }
                }
            }
        }

        class ReplaceWithActualProductNameSuggestion(original: String, suggested: String, position: Position, rule: Rule, val product: Product)
            : SimpleSuggestion(original, suggested, position, rule)
    }

    class UnitsRule(private val units: List<Unit>) : Rule() {

        constructor(vararg unit: Unit) : this(unit.toList())

        override fun invoke(input: Input): List<Suggestion> = rule(input) {
            var currentNumber = ""

            val text = input.text + " "
            text.forEachIndexed { i, it ->
                val position = Position(
                        offset = i - currentNumber.length,
                        length = currentNumber.length
                )
                when (it) {
                    ' '                                                        -> {
                        val textAfterNumber = text.substring(i)
                        if (currentNumber.isNotBlank() &&
                            !listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9').none {
                                currentNumber.contains(it)
                            } &&
                            units.none { textAfterNumber.startsWith(" $it") }
                        ) {
                            units.forEach {
                                val number =
                                        currentNumber.run {
                                            if (endsWith(",")) substringBeforeLast(",")
                                            else if (endsWith(".")) substringBeforeLast(".") else this
                                        }
                                suggest(
                                        AddUnitSuggestion(
                                                number,
                                                "$number ${it.name}",
                                                position,
                                                this@UnitsRule
                                        )
                                )
                            }
                        }
                        currentNumber = ""
                    }
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', ',' -> {
                        currentNumber += it
                    }
                    else                                                       -> currentNumber = ""
                }
            }
        }

        class AddUnitSuggestion(original: String, suggested: String, position: Position, rule: Rule)
            : SimpleSuggestion(original, suggested, position, rule)
    }

}
