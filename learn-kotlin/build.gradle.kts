/*

mainClassName = 'com.greatdreams.learn.kotlin.MainProgram'
// applicationDefaultJvmArgs = [】

def kotlinxCoroutineVersion = '0.23.3'
def korVersion = '0.20.0'

dependencies {
    compile "org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlinxCoroutineVersion}"
    compile "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${kotlinxCoroutineVersion}"
    compile "org.jetbrains.kotlinx:kotlinx-coroutines-io:${kotlinxCoroutineVersion}"
    compile "org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${kotlinxCoroutineVersion}"
    compile "org.jetbrains.kotlinx:kotlinx-coroutines-nio:${kotlinxCoroutineVersion}"
    compile "org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${kotlinxCoroutineVersion}"
    compile "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${kotlinxCoroutineVersion}"
    compile "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${kotlinxCoroutineVersion}"
    compile "org.jetbrains.kotlinx:kotlinx-coroutines-javafx:${kotlinxCoroutineVersion}"
    compile "org.jetbrains.kotlinx:kotlinx-coroutines-quasar:${kotlinxCoroutineVersion}"
    compile "org.jetbrains.kotlinx:kotlinx-coroutines-guava:${kotlinxCoroutineVersion}"

    compile "com.soywiz:korio:$korVersion"

}*/

import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

import org.gradle.api.plugins.JavaPluginConvention

buildscript {
    val repos = listOfNotNull(
            "https://jcenter.bintray.com/",
            "https://plugins.gradle.org/m2",
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

    val junitVersion = "1.2.0"
    dependencies {
        classpath("org.junit.platform:junit-platform-gradle-plugin:$junitVersion")
    }
}
plugins {
    java
    application
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish`
}

allprojects {
    apply {
        plugin("java")
        plugin("application")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("maven-publish")
        plugin("org.jetbrains.dokka")
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

    dependencies {
        val slf4jVersion = "1.7.25"
        val logbackVersion = "1.2.3"
        val groovyVersion = "2.5.2"

        val spekVersion = "1.2.0"
        val kluentVersion = "1.15"
        val harmkrest = "1.4.2.2"
        val winterbVersion = "0.5.0"
        val junitVersion = "1.2.0"

        val kotlinxCoroutineVersion = "0.24.0"
        val korVersion = "0.20.0"


        compile(kotlin("stdlib"))
        compile(kotlin("reflect"))


        compile("org.slf4j:slf4j-api:${slf4jVersion}")
        compile("org.slf4j:jul-to-slf4j:${slf4jVersion}")
        compile("ch.qos.logback:logback-core:$logbackVersion")
        compile("ch.qos.logback:logback-classic:$logbackVersion")
        compile("ch.qos.logback:logback-access:$logbackVersion")
        compile("org.codehaus.groovy:groovy-all:$groovyVersion")

        compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlinxCoroutineVersion}")
        compile("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${kotlinxCoroutineVersion}")
        compile("org.jetbrains.kotlinx:kotlinx-coroutines-io:${kotlinxCoroutineVersion}")
        compile("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${kotlinxCoroutineVersion}")
        compile("org.jetbrains.kotlinx:kotlinx-coroutines-nio:${kotlinxCoroutineVersion}")
        compile("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${kotlinxCoroutineVersion}")
        compile("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${kotlinxCoroutineVersion}")
        compile("org.jetbrains.kotlinx:kotlinx-coroutines-rx2:${kotlinxCoroutineVersion}")
        compile("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:${kotlinxCoroutineVersion}")
        compile("org.jetbrains.kotlinx:kotlinx-coroutines-quasar:${kotlinxCoroutineVersion}")
        compile("org.jetbrains.kotlinx:kotlinx-coroutines-guava:${kotlinxCoroutineVersion}")
        compile("com.soywiz:korio:$korVersion")


        testCompile("org.jetbrains.spek:spek-api:$spekVersion") {
            exclude("org.jetbrains.kotlin")
        }
        testCompile("org.jetbrains.spek:spek-junit-platform-engine:$spekVersion") {
            exclude("org.junit.platform")
        }

        testCompile("org.amshove.kluent:kluent:$kluentVersion")
        testCompile("com.natpryce:hamkrest:$harmkrest")
        testCompile("com.winterbe:expekt:${winterbVersion}")
        testCompile("org.junit.platform:junit-platform-runner:$junitVersion")

    }

    application {
        mainClassName = "com.greatdreams.learn.kotlin.MainProgram"
    }



    val sourcesJar by tasks.creating(Jar::class) {
        classifier = "sources"
        from(sourceSets["main"].allSource)
    }

    val dokkaJavadoc by tasks.creating(DokkaTask::class) {
        outputFormat = "javadoc"
        outputDirectory = "$buildDir/dokkaJavadoc"
        noStdlibLink = true
    }


    val javadocJar by tasks.creating(Jar::class) {
        classifier = "javadoc"
        from(dokkaJavadoc)
    }


    kotlin {
        experimental.coroutines = Coroutines.ENABLE
    }

    publishing {
        repositories {
            maven {
                name = "local"
                url = uri("$buildDir/repo")
            }
            maven {
                name = "lab"
                url = uri("http://nexus.buptnsrc.com/content/repositories/thirdparty/")
                credentials {
                    username = "greatdreams"
                    password = "893557whw"
                }
            }
        }

        if (!project.hasProperty("jenkins")) {
            println("Property 'jenkins' not set. Publishing only to MavenLocal")
        } else {
            (publications) {
                "maven"(MavenPublication::class) {
                    from(components["java"])
                    artifact(sourcesJar)
                    artifact(javadocJar)
                }

            }
        }
    }
    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
        withType<Test> {
            testLogging.showStandardStreams = true
        }
        withType<Jar> {
            manifest {
                attributes["Main-Class"] = application.mainClassName
            }
            from(configurations.runtime.map { if (it.isDirectory) it else zipTree(it) })
        }
        withType<GradleBuild> {
            finalizedBy("publishToMavenLocal")
        }

        // Use the native JUnit support of Gradle.
        "test"(Test::class) {
            useJUnitPlatform {
                includeEngines("spek")
            }
            reports {
                junitXml.setEnabled(true)
                html.setEnabled(true)
            }
        }
    }
}