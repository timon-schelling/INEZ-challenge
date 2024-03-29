package klang.impl

import klang.input.Input
import klang.rule
import klang.rule.Rule
import klang.suggestion.Position
import klang.suggestion.SimpleSuggestion
import klang.suggestion.Suggestion
import scala.util.matching.Regex

data class Synonym(val original: String, val replacement: String)

/**
 * [Rule] used to replace given [synonyms]
 * @property synonyms a list of synonym's that might be replaced
 */
open class SynonymReplaceRule(private val synonyms: List<Synonym>) : Rule() {

    override fun invoke(input: Input): List<Suggestion> = rule(input) {
        synonyms.forEach {
            var index = 0
            while (index < input.text.length) {
                val positionOfProductString = input.text.indexOf(it.original, index)
                if (positionOfProductString >= 0) {
                    index = positionOfProductString
                    suggest(ReplaceSynonymSuggestion(it.original, it.replacement, Position(index, it.original.length), this@SynonymReplaceRule))
                    index++
                } else {
                    index = input.text.length
                }
            }
        }
    }

    class ReplaceSynonymSuggestion(original: String, suggested: String, position: Position, rule: Rule)
        : SimpleSuggestion(original, suggested, position, rule)
}
