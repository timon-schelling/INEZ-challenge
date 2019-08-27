package klang.suggestion

import klang.rule.Rule
import klang.suggestion.Position

/**
 * used to describe a suggested text change
 */
abstract class Suggestion {

    /**
     * the text that might be replaced
     */
    abstract val original: String

    /**
     * the suggested text
     */
    abstract val suggested: String

    /**
     * [Position] of [original] in [Input.text]
     */
    abstract val position: Position

    /**
     * the rule that created this [Suggestion]
     */
    abstract val rule: Rule

    /**
     * a human-understandable description of this [Suggestion]
     */
    val message: String get() = message()

    /**
     * should be overwritten by any [Suggestion]
     * @return a human-understandable description of this [Suggestion]
     */
    abstract fun message(): String

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
