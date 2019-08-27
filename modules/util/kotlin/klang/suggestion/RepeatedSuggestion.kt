package klang.suggestion

import klang.rule.Rule

/**
 * used to group duplicated suggestions
 * @constructor
 * @param original
 * @param suggested
 * @param position
 * @param rule
 * @see Suggestion
 */
class RepeatedSuggestion(original: String, suggested: String, position: Position, rule: Rule)
    : SimpleSuggestion(original, suggested, position, rule), MutableList<Suggestion> by mutableListOf()
