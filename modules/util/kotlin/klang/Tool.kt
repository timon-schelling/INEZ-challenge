package klang

import klang.rule.Rule
import klang.rule.RuleGroup

class Tool(rules: List<Rule> = listOf()) : RuleGroup(rules = rules.toMutableList())
