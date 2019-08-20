import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import util.*

plugins {
    java
    kotlin("jvm") version Versions.kotlin
    id("kotlinx-serialization") version Versions.kotlin
}

group = "de.timokrates"
version = "1.0"

allprojects {
    repositories {
        mavenCentral()
        google()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://dl.bintray.com/soywiz/soywiz")
    }
    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
}

subprojects {
    version = rootProject.version
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("kotlinx-serialization")
    }
    kotlin {
        jvmTarget = "1.8"
    }
    tasks {
        "jar"(Jar::class) {
            archiveFileName.set(
                    "${rootProject.name}${this@subprojects.path.replace(
                            ":",
                            "-"
                    )}-${this@subprojects.version}.jar"
            )
        }
    }
}

tasks {
    "build" {
        dependsOnSubProjectsTask()
        finalizedBy("copyDependenciesJars")
        finalizedBy("copySubProjectJars")
        finalizedBy("copyStaticFiles")
        finalizedBy("copyLibs")
        finalizedBy("copyDist")
    }
    "clean" {
        dependsOnSubProjectsTask()
    }
    "jar"(Jar::class) {
        dependsOnSubProjectsTask()
        archiveFileName.set("${rootProject.name}-${rootProject.version}.jar")
    }
}


val copyDependenciesJars = task("copyDependenciesJars", Copy::class) {
    includeEmptyDirs = true
    subprojects.forEach {
        from(
                configurations.archives.map {
                    it.asFileTree
                }
        )
    }
    into("$buildDir/libs")
}

val copySubProjectJars = task("copySubProjectJars", Copy::class) {
    includeEmptyDirs = true
    subprojects.forEach { from("${it.buildDir.absolutePath}/libs") }
    into("$buildDir/libs")
}

val copyStaticFiles = task("copyStaticFiles", Copy::class) {
    from("static")
    into("$buildDir/dist")
}

val copyLibs = task("copyLibs", Copy::class) {
    dependsOn("copyDependenciesJars")
    dependsOn("copySubProjectJars")
    from("$buildDir/libs")
    into("$buildDir/dist/lib")
}

val copyDist = task("copyDist", Copy::class) {
    dependsOn("copyLibs")
    from("$buildDir/dist")
    into("dist")
}


defaultProject()
