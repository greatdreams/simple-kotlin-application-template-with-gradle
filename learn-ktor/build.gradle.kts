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
}
plugins {
    java
    application
    kotlin("jvm")
    id("org.jetbrains.dokka")
    `maven-publish`
    signing
}

group = "com.greatdreams.lean.kotlin"
version = "0.0.1"

allprojects {
    apply {
        plugin("java")
        plugin("application")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("maven-publish")
        plugin("org.jetbrains.dokka")
        plugin("signing")
    }

    repositories {
        mavenCentral()
        jcenter()
        maven {
            url = uri("https://dl.bintray.com/kotlin/kotlin-dev/")
        }
        maven {
            url = uri("https://dl.bintray.com/kotlin/ktor")
        }
        maven {
            url = uri("https://dl.bintray.com/kotlin/kotlinx")
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

        val slf4jVersion: String by project
        val logbackVersion: String by project
        val groovyVersion: String by project

        val spekVersion: String by project
        val kluentVersion: String by project
        val harmkrest: String by project
        val winterbVersion: String by project
        val junitVersion: String by project

        val spek2Version: String by project

        val ktorVersion: String by project

        compile(kotlin("stdlib-common"))
        compile(kotlin("stdlib"))
        compile(kotlin("reflect"))
        compile(kotlin("stdlib-jdk8"))

        compile("io.ktor:ktor-server-netty:$ktorVersion")
        compile("io.ktor:ktor-client-apache:$ktorVersion")
        compile("io.ktor:ktor-client-cio:$ktorVersion")
        compile("io.ktor:ktor-client-jetty:$ktorVersion")

        compile("org.slf4j:slf4j-api:$slf4jVersion")
        compile("org.slf4j:jul-to-slf4j:$slf4jVersion")
        compile("ch.qos.logback:logback-core:$logbackVersion")
        compile("ch.qos.logback:logback-classic:$logbackVersion")
        compile("ch.qos.logback:logback-access:$logbackVersion")
        compile("org.codehaus.groovy:groovy-all:$groovyVersion")

        // add spek2 (a test framework) to dependencies
        testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spek2Version") {
            exclude("org.jetbrains.kotlin")
        }
        testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spek2Version") {
            exclude("org.junit.platform")
            exclude("org.jetbrains.kotlin")
        }
        // add spek (a test framework) to dependencies
        testCompile("org.jetbrains.spek:spek-api:$spekVersion") {
            exclude("org.jetbrains.kotlin")
        }
        testCompile("org.jetbrains.spek:spek-junit-platform-engine:$spekVersion") {
            exclude("org.jetbrains.kotlin")
            exclude("org.junit.platform")
        }

        testCompile("org.amshove.kluent:kluent:$kluentVersion")
        testCompile("com.natpryce:hamkrest:$harmkrest")
        testCompile("com.winterbe:expekt:$winterbVersion")
        testCompile("org.junit.jupiter:junit-jupiter-api:$junitVersion")
        testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    }

    application {
        mainClassName = "com.greatdreams.learn.kotlin.ktor.ClientKt"
    }

/*    val sourcesJar by tasks.creating(Jar::class) {
        classifier = "sources"
        from(sourceSets["main"].allSource)
    }*/

    task<Jar>("sourceJar") {
        classifier = "sources"

        from(sourceSets["main"].allJava)
    }
    task<Jar>("javadocJar") {
        classifier = "javadoc"

        from(tasks["javadoc"])
        from(tasks["dokka"])
    }

    /*
    val dokkaJavadoc by tasks.creating(DokkaTask::class) {
            outputFormat = "html"
            outputDirectory = "$buildDir/dokkaJavadoc"
            noStdlibLink = true
    }

    val javadocJar by tasks.creating(Jar::class) {
        classifier = "javadoc"
        from(dokka)
    }
    */


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
/*
        if (!project.hasProperty("jenkins")) {
            println("Property 'jenkins' not set. Publishing only to MavenLocal")
        } else {
            (publications) {
                "maven"(MavenPublication::class) {
                    from(components["java"])
                    artifact(sourcesJar)
                }

            }
        }*/

        publications {
            create<MavenPublication>("mavenJava") {
                // artifact(tasks["distZip"])
                // artifact(tasks["customDistTar"])
                from(components["java"])
                artifact(tasks["sourceJar"])
                artifact(tasks["javadocJar"])

                pom {
                    name.set("My Library")
                    description.set("A concise description of my library")
                    url.set("http://github.com/greatedreams")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("johnd")
                            name.set("John Doe")
                            email.set("john.doe@example.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:git://example.com/my-library.git")
                        developerConnection.set("scm:git:ssh://example.com/my-library.git")
                        url.set("http://example.com/my-library/")
                    }
                }
            }
        }
    }

    /*
    signing {
        sign(publishing.publications["mavenJava"])
    }
    */

    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
        withType<DokkaTask> {
            outputFormat = "html"
            outputDirectory = "$buildDir/javadoc"
            noStdlibLink = true
        }
        withType<Javadoc> {
        }
        withType<Jar> {
            manifest {
                attributes["Implementation-Titile"] = "Gradle"
                attributes["Main-Class"] = application.mainClassName
            }
            // from(configurations.runtime.map { if (it.isDirectory) it else zipTree(it) })
            from(configurations.runtime.map {
                it
            })
        }
        withType<GradleBuild> {
            finalizedBy("publishToMavenLocal")
        }

        // Use the native JUnit support of Gradle.
        "test"(Test::class) {
            testLogging.showStandardStreams = true

            useJUnitPlatform {
                includeEngines("spek")
                includeEngines("spek2")
            }
            reports {
                junitXml.setEnabled(true)
                html.setEnabled(true)
            }
        }
    }

    distributions {
        create("custom") {

        }
        getByName("main") {
            // name = "${project.name}-${project.version}"

            contents {
                from("README.md")
            }
        }
    }
}