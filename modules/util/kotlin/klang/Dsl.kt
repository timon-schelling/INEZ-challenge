package klang

import amber.collections.copyAllValues
import amber.collections.moveAllValues
import klang.input.Input
import klang.input.SimpleInput
import klang.rule.Rule
import klang.suggestion.RepeatedSuggestion
import klang.suggestion.Suggestion

class LambdaRuleScope(input: Input, private val suggestions: MutableList<Suggestion>, val rule: Rule) : Input by input{
    fun suggest(suggestion: Suggestion) {
        suggestions.add(suggestion)
    }
    fun suggest(vararg suggestion: Suggestion) {
        suggestion.forEach {
            suggest(it)
        }
    }
    fun suggest(suggestions: List<Suggestion>) {
        suggestions.forEach { suggest(it) }
    }
    operator fun Suggestion.unaryMinus() = suggest(this)
}

open class LambdaRule(private val block: LambdaRuleScope.() -> Unit) : Rule() {
    override fun invoke(input: Input): List<Suggestion> {
        val suggestions = mutableListOf<Suggestion>()
        LambdaRuleScope(input, suggestions, this).block()
        return suggestions.toList()
    }
}

fun rule(input: Input, block: LambdaRuleScope.() -> Unit) = rule(block).invoke(input)

fun rule(block: LambdaRuleScope.() -> Unit) = LambdaRule(block)

class ToolAssemblyScope(private val tool: Tool){
    fun add(rule: Rule) = tool.add(rule)
    operator fun Rule.unaryPlus() = add(this)
}

fun tool(block: ToolAssemblyScope.() -> Unit): Tool {
    val tool = Tool()
    ToolAssemblyScope(tool).apply(block)
    return tool
}

fun Rule.check(text: String) = invoke(SimpleInput(text))

fun String.apply(suggestion: Suggestion) = listOf(
        slice(0 until suggestion.position.offset),
        slice(suggestion.position.offset + suggestion.position.length until length)
).joinToString(separator = suggestion.suggested)

fun MutableList<Suggestion>.groupRepeatedSuggestions() {
    val newValues = mutableListOf<Suggestion>().apply {
        this@groupRepeatedSuggestions.moveAllValues(this)
    }.forEach { suggestion ->
        val current = find {
            it.original == suggestion.original &&
            it.suggested == suggestion.suggested &&
            it.position == suggestion.position
        }
        when (current) {
            null                  -> this.add(suggestion)
            is RepeatedSuggestion -> current.add(suggestion)
            else                  -> {
                remove(current)
                add(
                        RepeatedSuggestion(
                                current.original,
                                current.suggested,
                                current.position,
                                current.rule
                        ).apply {
                            this@apply.add(suggestion)
                        }
                )
            }
        }
    }
}

fun MutableList<Suggestion>.sortByRepetitions() {
    sortBy {
        if (it is RepeatedSuggestion) {
            it.size
        } else 0
    }
    reverse()
}
