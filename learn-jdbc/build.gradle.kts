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
        val slf4jVersion: String by project
        val logbackVersion: String by project
        val groovyVersion: String by project

        val spekVersion: String by project
        val kluentVersion: String by project
        val harmkrest: String by project
        val winterbVersion: String by project
        val junitVersion : String by project

        val mysqlDriverVersion: String by project
        val postgresqlDriverVersion: String by project
        val sqliteDriverVersion: String by project
        val derbyDriverVersion : String by project
        val h2DriverVersion : String by project
        val hsqldbDriverVersion : String by project

        val hikariCPVersion: String by project
        val dbcp2Version : String by project


        compile(kotlin("stdlib"))
        compile(kotlin("reflect"))


        compile("org.slf4j:slf4j-api:${slf4jVersion}")
        compile("org.slf4j:jul-to-slf4j:${slf4jVersion}")
        compile("ch.qos.logback:logback-core:$logbackVersion")
        compile("ch.qos.logback:logback-classic:$logbackVersion")
        compile("ch.qos.logback:logback-access:$logbackVersion")
        compile("org.codehaus.groovy:groovy-all:$groovyVersion")

        /**
         * mysql, postgresql, sqlite database driver for jdbc
         */
        compile("mysql:mysql-connector-java:$mysqlDriverVersion")
        compile("org.postgresql:postgresql:$postgresqlDriverVersion")
        compile("org.xerial:sqlite-jdbc:$sqliteDriverVersion")
        compile("org.apache.derby:derby:$derbyDriverVersion")
        compile("com.h2database:h2:$h2DriverVersion")
        compile("org.hsqldb:hsqldb:$hsqldbDriverVersion")

        /**
         * Apache Commons DBCP software imiplements Database Connection Pooling
         */
        compile("org.apache.commons:commons-dbcp2:$dbcp2Version")

        // HikariCP Database Connection
        compile("com.zaxxer:HikariCP:$hikariCPVersion")


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
        mainClassName = "com.greatdreams.learn.jdbc.MainProgram"
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
            // from(configurations.runtime.map { if (it.isDirectory) it else zipTree(it) })
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