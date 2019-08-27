package klang.suggestion

import klang.rule.Rule

open class SimpleSuggestion(
        override val original: String,
        override val suggested: String,
        override val position: Position,
        override val rule: Rule
) : Suggestion() {
    override fun message() = suggested
    override fun toString(): String {
        return "Suggestion(original='$original', suggested='$suggested', position=$position, rule=$rule)"
    }
}
