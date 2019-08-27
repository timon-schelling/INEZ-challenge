import util.*

plugins {
    kotlin("jvm")
}

dependencies {
    compile(Deps.kotlin)
    compile(Deps.kotlinCoroutines)
    compile(Deps.kotlinSerializationRuntime)
    compile(Deps.klock)
    compile(Deps.Jvm.jLanguageTool)
    compile(Deps.Jvm.regexDsl)
    compile(Deps.Jvm.completly)
}

defaultProject()
