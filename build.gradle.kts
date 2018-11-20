import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val repos = listOfNotNull(
            "https://plugins.gradle.org/m2",
            "https://jcenter.bintray.com/",
	    "https://dl.bintray.com/kotlin/kotlin-eap/",
            "https://dl.bintray.com/kotlin/kotlin-dev/",
            "http://dl.bintray.com/kotlin/kotlinx",
            "https://repo.gradle.org/gradle/libs-releases-local", // for native-platform
            "https://jetbrains.bintray.com/intellij-third-party-dependencies", // for jflex
            "https://dl.bintray.com/jetbrains/markdown" // for org.jetbrains:markdown
    )
    repositories {
        for (repo in repos) {
            maven(url = repo)
        }
    }
}

val dokkaVersion = "0.9.16"

plugins {
    java
    application
    kotlin("jvm").version("1.3.0")
    id("org.jetbrains.kotlin.plugin.allopen").version("1.3.10")
    id("org.jetbrains.dokka").version("0.9.17")
    `maven-publish`
}

repositories {

    mavenCentral()

    jcenter()

    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlin-dev/")
    }

    maven {
        url = uri("https://repo.huaweicloud.com/repository/maven/")
        credentials {
            username = "greatdreams"
            password = "Hd-0_Ct0u_R"
        }
    }
}

tasks {
    withType(Wrapper::class.java) {
        gradleVersion = "5.0-rc-4"
    }
}
dependencies {
    compile(kotlin("stdlib-jdk8"))
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}