package klang

import klang.rule.Rule
import klang.rule.RuleGroup

/**
 * used to suggest text changes based on given [rules]
 * @constructor
 * @param rules rules ude to suggest text changes
 * @see Rule
 */
class Tool(rules: List<Rule> = listOf()) : RuleGroup(rules = rules.toMutableList())
