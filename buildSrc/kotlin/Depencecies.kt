object Versions {
    val kotlin = "1.3.41"
    val kotlinCoroutines = "1.2.1"
    val tornadofx = "1.7.16"
    val fontawesomefx= "8.9"
    val klock = "1.0.0"
    val kotlinSerializationRuntime = "0.11.1"
    val jLanguageTool = "4.6"
    val jmetro = "4.2"
    val completly = "0.8.0"
    val regexDsl = "v0.1"
}

object Deps {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    val kotlinCoroutinesJavaFX = "org.jetbrains.kotlinx:kotlinx-coroutines-javafx:${Versions.kotlinCoroutines}"
    val klock = "com.soywiz:klock-jvm:${Versions.klock}"
    val kotlinSerializationRuntime =
            "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.kotlinSerializationRuntime}"

    object Jvm {
        val tornadofx = "no.tornado:tornadofx:${Versions.tornadofx}"
        val jmetro = "org.jfxtras:jmetro:${Versions.jmetro}"
        val fontawesomefx = "de.jensd:fontawesomefx:${Versions.fontawesomefx}"
        val jLanguageTool = "org.languagetool:language-all:${Versions.jLanguageTool}"
        val completly = "com.miguelfonseca.completely:completely-core:${Versions.completly}"
        val regexDsl = "com.github.h0tk3y:regex-dsl:${Versions.regexDsl}"
    }
}
