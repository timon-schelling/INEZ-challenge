package klang.impl

import amber.trial.trial
import amber.trial.tryAndForget
import klang.input.Input
import klang.rule
import klang.rule.Rule
import klang.suggestion.Position
import klang.suggestion.SimpleSuggestion
import klang.suggestion.Suggestion
import com.miguelfonseca.completely.text.analyze.transform.LowerCaseTransformer
import com.miguelfonseca.completely.AutocompleteEngine
import com.miguelfonseca.completely.text.analyze.tokenize.WordTokenizer
import com.miguelfonseca.completely.text.match.EditDistanceAutomaton
import com.miguelfonseca.completely.data.ScoredObject
import com.miguelfonseca.completely.text.index.PatriciaTrie
import com.miguelfonseca.completely.IndexAdapter
import com.miguelfonseca.completely.data.Indexable
import java.text.BreakIterator
import kotlin.math.ln
import kotlin.math.max


class AutoCompleteRule(words: List<String>) : Rule() {

    private val engine = AutocompleteEngine.Builder<Record>()
            .setIndex(Adapter())
            .setAnalyzers(LowerCaseTransformer(), WordTokenizer())
            .build()
            .apply{
                for (word in words) {
                    add(Record(word))
                }
            }

    override fun invoke(input: Input): List<Suggestion> = rule(input) {
        val breakIterator = BreakIterator.getWordInstance()
        breakIterator.setText(text)
        var lastIndex = breakIterator.first()
        while (BreakIterator.DONE != lastIndex) {
            val firstIndex = lastIndex
            lastIndex = breakIterator.next()
            if (lastIndex != BreakIterator.DONE && Character.isLetterOrDigit(text[firstIndex])) {
                val word = text.substring(firstIndex, lastIndex)
                val position = Position(firstIndex, lastIndex - firstIndex)
                tryAndForget {
                    engine.search(word).forEach {
                        if (it.name != word) suggest(AutocompleteSuggestion(word, it.name, position, this@AutoCompleteRule))
                    }
                }
            }
        }
    }

    private class Adapter : IndexAdapter<Record> {
        private val index = PatriciaTrie<Record>()

        override fun get(token: String): Collection<ScoredObject<Record>> {
            val threshold = ln(max(token.length - 1, 1).toDouble())
            return index.getAny(EditDistanceAutomaton(token, threshold))
        }

        override fun put(token: String, value: Record?): Boolean {
            return index.put(token, value)
        }

        override fun remove(value: Record): Boolean {
            return index.remove(value)
        }
    }

    private class Record(val name: String) : Indexable {

        override fun getFields(): List<String> {
            return listOf(name)
        }
    }

    class AutocompleteSuggestion(original: String, suggested: String, position: Position, rule: Rule)
        : SimpleSuggestion(original, suggested, position, rule)
}

