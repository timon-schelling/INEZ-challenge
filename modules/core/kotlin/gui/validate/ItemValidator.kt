package gui.validate

import com.github.h0tk3y.regexDsl.regex
import gui.models.Item
import gui.models.MainModel

object ItemValidator {
    private val whitespaceRegex = regex {
        whitespace()
    }
    private val validItemRegex = regex {
        startOfString()
        optional {
            oneOrMore { digit() }
            optional {
                anyOf(",", ".")
                oneOrMore { digit() }
            }
        }
        whitespace()
        oneOrMore { anyChar() }
        endOfString()
    }
    fun Item.isValid() = text.matches(validItemRegex)
    fun Item.digit() = text.substringBefore(whitespaceRegex.find(text)?.value!!).replace(",", ".").toDouble()
    fun Item.text() = text.substringAfter(whitespaceRegex.find(text)?.value!!)
}
