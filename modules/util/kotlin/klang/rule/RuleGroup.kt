package klang.rule

import klang.input.Input
import klang.suggestion.Suggestion

/**
 * used to group [rules] and make them usable like one
 * @property rules
 */
open class RuleGroup(private val rules: MutableList<Rule> = mutableListOf()) : Rule() {

    constructor(vararg rule: Rule) : this(mutableListOf(*rule))

    override fun invoke(input: Input): List<Suggestion> {
        val result = mutableListOf<Suggestion>()
        rules.forEach {
            it.invoke(input).forEach {
                result.add(it)
            }
        }
        return result
    }

    fun add(rule: Rule) = rules.add(rule)
}
