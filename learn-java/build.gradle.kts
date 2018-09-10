import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayInputStream

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
  /*  apply(plugin = "java")
    apply(plugin = "application")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")*/

    apply {
        plugin("java")
        plugin("application")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("maven-publish")
    }

    repositories {
        maven {
            url = uri("https://dl.bintray.com/kotlin/kotlin-dev/")
        }
        mavenCentral()
        jcenter()

        maven {
            url = uri("https://repo.huaweicloud.com/repository/maven/")
            credentials {
                username = "greatdreams"
                password = "Hd-0_Ct0u_R"
            }
        }
    }

    project.dependencies {

        val bouncyVersion = "1.60"
        val commonsCodecVersion = "1.10"

        val slf4jVersion = "1.7.25"
        val logbackVersion = "1.2.3"
        val groovyVersion = "2.5.2"

        val spekVersion = "1.2.0"
        val kluentVersion = "1.15"
        val harmkrest = "1.4.2.2"
        val winterbVersion = "0.5.0"
        val junitVersion = "1.2.0"
        val junitJupiterVersion = "5.2.0"

        compile(kotlin("stdlib"))
        compile(kotlin("reflect"))

        compile("org.bouncycastle:bcprov-jdk15on:$bouncyVersion")
        compile("org.bouncycastle:bcprov-ext-jdk15on:$bouncyVersion")
        compile("org.bouncycastle:bcprov-ext-debug-jdk15on:$bouncyVersion")
        compile("org.bouncycastle:bcprov-debug-jdk15on:$bouncyVersion")
        compile("org.bouncycastle:bcpkix-jdk15on:$bouncyVersion")
        compile("org.bouncycastle:bcmail-jdk15on:$bouncyVersion")
        compile("org.bouncycastle:bcpg-jdk15on:$bouncyVersion")
        compile("org.bouncycastle:bctls-jdk15on:$bouncyVersion")

        compile("commons-codec:commons-codec:$commonsCodecVersion")

        compile("org.slf4j:slf4j-api:$slf4jVersion")
        compile("org.slf4j:jul-to-slf4j:$slf4jVersion")
        compile("ch.qos.logback:logback-core:$logbackVersion")
        compile("ch.qos.logback:logback-classic:$logbackVersion")
        compile("ch.qos.logback:logback-access:$logbackVersion")
        compile("org.codehaus.groovy:groovy-all:$groovyVersion")


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

        testCompile("org.junit.jupiter:junit-jupiter-api:${junitJupiterVersion}")
        testCompile("org.junit.jupiter:junit-jupiter-engine:${junitJupiterVersion}")
        testCompile("org.junit.jupiter:junit-jupiter-params:${junitJupiterVersion}")
        testCompile("org.junit.jupiter:junit-jupiter-migrationsupport:${junitJupiterVersion}")
    }
    val sourcesJar by tasks.creating(Jar::class) {
        classifier = "sources"
        // from(java.sourceSets["main"].allSource)
    }

    val dokkaJavadoc by tasks.creating(org.jetbrains.dokka.gradle.DokkaTask::class) {
        outputFormat = "javadoc"
        outputDirectory = "$buildDir/dokkaJavadoc"
        noStdlibLink = true
    }


    val javadocJar1 by tasks.creating(Jar::class) {
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
                    artifact(javadocJar1)
                }

            }
        }
    }
    tasks {
        withType<JavaExec> {
            standardInput = System.`in`
            maxHeapSize = "200M"
        }
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
        withType<Test> {
            testLogging.showStandardStreams = true
        }
        withType<Jar> {
            manifest {
                // attributes["Main-Class"] = application.mainClassName
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


application {
    mainClassName = "com.greatdreams.learn.java.MainProgram"
    applicationDefaultJvmArgs = listOf("-Dfile.encoding=UTF-8", "-Djavax.net.debug=all")
}

tasks.create<JavaExec>("startQuoteClient") {
    main = "com.greatdreams.learn.java.net.udp.QuoteClient"
    classpath = sourceSets["main"].runtimeClasspath
}