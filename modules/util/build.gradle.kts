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
    compile("com.miguelfonseca.completely:completely-core:0.8.0")
}

defaultProject()
