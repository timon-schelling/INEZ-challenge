package klang.suggestion

import klang.rule.Rule

class RepeatedSuggestion(original: String, suggested: String, position: Position, rule: Rule)
    : SimpleSuggestion(original, suggested, position, rule), MutableList<Suggestion> by mutableListOf()
