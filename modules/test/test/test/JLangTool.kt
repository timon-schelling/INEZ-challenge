package test

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import klang.check
import klang.impl.AutoCompleteRule
import klang.impl.JLanguageToolRule
import klang.tool
import org.languagetool.language.GermanyGerman

class JLangTool : StringSpec() {
    init {
        "simple spelling check with JLanguageToolRule" {
            val someRandomGermanTextWithSpellingIssues = "Hallo. Ich ben ein kliner Blindtext. Und zwar schan so longe ich denken kann."
            val tool = tool {
                +JLanguageToolRule(GermanyGerman())
            }
            val suggestions = tool.check(someRandomGermanTextWithSpellingIssues)
            suggestions.size shouldBe 80
        }

        "simple autocomplete with AutoComplete" {
            val germanTextWithAutocompletionPoetical = "Mil"
            val tool = tool {
                +AutoCompleteRule("Milch")
            }
            val suggestions = tool.check(germanTextWithAutocompletionPoetical)
            suggestions.size shouldBe 1
        }
    }
}
