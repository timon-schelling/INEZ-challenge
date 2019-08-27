package klang.rule

import klang.input.Input
import klang.suggestion.Suggestion

/**
 * should be extended by rule classes to provide suggestions for a given [Input]
 * @see Suggestion
 */
abstract class Rule {
    /**
     * should be overwritten by rule classes to provide suggestions for a given [input]
     * @param input
     * @return a list of suggestions provided by this rule
     * @see Suggestion
     */
    abstract operator fun invoke(input: Input): List<Suggestion>
}
