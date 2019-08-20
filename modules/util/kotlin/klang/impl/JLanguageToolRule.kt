package klang.impl

import amber.sync.Synchronized
import klang.input.Input
import klang.rule
import klang.rule.Rule
import klang.suggestion.Position
import klang.suggestion.SimpleSuggestion
import klang.suggestion.Suggestion
import org.languagetool.JLanguageTool
import org.languagetool.Language

class JLanguageToolRule(private val tool: JLanguageTool) : Rule(), Synchronized by Synchronized() {
    constructor(language: Language) : this(JLanguageTool(language))
    override fun invoke(input: Input): List<Suggestion> = rule(input) {
        val matches = synchronizedSafe {
            tool.check(text)
        }
        for (match in matches) {
            val offset = match.fromPos
            val length = match.toPos - offset
            val position = Position(offset, length)
            match.suggestedReplacements.forEach {
                suggest(
                        JLanguageToolSuggestion(
                                text.substring(offset, length + offset),
                                it,
                                position,
                                this@JLanguageToolRule
                        )
                )
            }
        }
    }

    class JLanguageToolSuggestion(original: String, suggested: String, position: Position, rule: Rule)
        : SimpleSuggestion(original, suggested, position, rule)
}
