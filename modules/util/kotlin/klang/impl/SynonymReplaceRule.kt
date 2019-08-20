package klang.impl

import klang.input.Input
import klang.rule
import klang.rule.Rule
import klang.suggestion.Position
import klang.suggestion.SimpleSuggestion
import klang.suggestion.Suggestion
import scala.util.matching.Regex

data class Synonym(val original: String, val replacement: String)

//todo redo allow multi word synonyms
open class SynonymReplaceRule(private val synonyms: List<Synonym>) : Rule() {

    override fun invoke(input: Input): List<Suggestion> = rule(input) {
        synonyms.forEach {
            var index = 0
            while (index < input.text.length) {
                val positionOfProductString = input.text.indexOf(it.original, index)
                index = if (positionOfProductString >= 0) {
                    suggest(ReplaceSynonymSuggestion(it.original, it.replacement, Position(index, it.original.length), this@SynonymReplaceRule))
                    positionOfProductString + 1
                } else input.text.length
            }
        }
    }

    class ReplaceSynonymSuggestion(original: String, suggested: String, position: Position, rule: Rule)
        : SimpleSuggestion(original, suggested, position, rule)
}
