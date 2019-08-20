plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    kotlin("jvm") version "1.3.21"
}

repositories {
    jcenter()
    mavenCentral()
    maven("https://kotlin.bintray.com/kotlinx")
    maven("https://dl.bintray.com/soywiz/soywiz")
}

dependencies {
    implementation("com.soywiz:klock-jvm:1.0.0")
}

the<JavaPluginConvention>().sourceSets.getByName("main").java.srcDirs("kotlin")
