import util.*

plugins {
    kotlin("jvm")
}

dependencies {
    compile(project(":util"))
    compile(project(":core"))
}

defaultProject()
