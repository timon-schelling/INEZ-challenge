package klang.rule

import klang.input.Input
import klang.suggestion.Suggestion

abstract class Rule {
    abstract operator fun invoke(input: Input): List<Suggestion>
}
