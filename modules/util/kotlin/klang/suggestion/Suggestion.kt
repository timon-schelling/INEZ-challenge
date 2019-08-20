package klang.suggestion

import klang.rule.Rule
import klang.suggestion.Position

abstract class Suggestion {
    abstract val original: String
    abstract val suggested: String
    abstract val position: Position
    abstract val rule: Rule

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SimpleSuggestion) return false

        if (original != other.original) return false
        if (suggested != other.suggested) return false
        if (position != other.position) return false
        if (rule != other.rule) return false

        return true
    }

    override fun hashCode(): Int {
        var result = original.hashCode()
        result = 31 * result + suggested.hashCode()
        result = 31 * result + position.hashCode()
        result = 31 * result + rule.hashCode()
        return result
    }
}
