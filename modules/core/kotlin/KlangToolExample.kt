import klang.check
import klang.impl.JLanguageToolRule
import klang.tool
import org.languagetool.language.GermanyGerman

fun main() {

    val someRandomGermanTextWithSpellingIssues = "Hallo. Ich ben ein kliner Blindtext. Und zwar schan so longe ich denken kann."

    //create new tool object
    val tool = tool {
        //add all rules here with unaryPlus or add
        +JLanguageToolRule(GermanyGerman())
    }

    //call check method on tool to execute all tool rules on someRandomGermanTextWithSpellingIssues
    val suggestions = tool.check(someRandomGermanTextWithSpellingIssues)

    //read and print suggestion data
    suggestions.forEachIndexed { i, it ->
        println("$i: ${it.original} --> ${it.suggested}")
    }

    /*
    output:
        0: ben --> Ben
        1: ben --> den
        2: ben --> bei
        3: ben --> oben
        4: ben --> per
        5: ben --> bin
        6: ben --> eben
        7: ben --> gen
        8: ben --> wen
        9: ben --> Yen
        10: ben --> peu
        11: ben --> säen
        12: ben --> Gen
        13: ben --> Ken
        14: ben --> Ren
        15: ben --> Säen
        16: ben --> Zen
        17: ben --> bäh
        18: ben --> peng
        19: ben --> Üben
        20: kliner --> kleiner
        21: kliner --> keiner
        22: kliner --> einer
        23: kliner --> seiner
        24: kliner --> keine
        25: kliner --> kleine
        26: kliner --> kleinen
        27: kliner --> keinen
        28: kliner --> kleinere
        29: kliner --> kleines
        30: kliner --> keinem
        31: kliner --> Oliver
        32: kliner --> linker
        33: kliner --> reiner
        34: kliner --> Rainer
        35: kliner --> feiner
        36: kliner --> grüner
        37: kliner --> keines
        38: kliner --> klarer
        39: kliner --> kleinem
        40: schan --> gab
        41: schan --> schon
        42: schan --> ganz
        43: schan --> gar
        44: schan --> schön
        45: schan --> gen
        46: schan --> gang
        47: schan --> schau
        48: schan --> schal
        49: schan --> gäb
        50: schan --> gähn
        51: schan --> ging
        52: schan --> gut
        53: schan --> sehen
        54: schan --> galt
        55: schan --> ganze
        56: schan --> zehn
        57: schan --> schuf
        58: schan --> gaben
        59: schan --> grün
        60: longe --> lange
        61: longe --> linke
        62: longe --> Mönche
        63: longe --> lohnte
        64: longe --> lohne
        65: longe --> lenke
        66: longe --> lösche
        67: longe --> Konche
        68: longe --> gonge
        69: longe --> locke
        70: longe --> logge
        71: longe --> lynche
        72: longe --> löge
        73: longe --> lang
        74: longe --> langen
        75: longe --> Folge
        76: longe --> Länge
        77: longe --> junge
        78: longe --> könne
        79: longe --> langer
     */
}
