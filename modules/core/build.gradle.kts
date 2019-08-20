import util.*

plugins {
    kotlin("jvm")
}

dependencies {
    compile(project(":util"))
    compile(Deps.kotlinCoroutinesJavaFX)
    compile(Deps.Jvm.tornadofx)
    compile(Deps.Jvm.fontawesomefx)
    compile(Deps.Jvm.jmetro)
}

defaultProject()
