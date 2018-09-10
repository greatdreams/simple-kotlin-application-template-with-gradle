import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.include

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
    application
    scala
    `maven-publish`
    id("com.github.maiflai.scalatest").version("0.22")
}

repositories {

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

    jcenter()
    mavenCentral()
}

dependencies {
    val scalaVersion = "2.13.0-M4"
    val scalatestVersion = "3.2.0-SNAP10"

    compile("org.scala-lang:scala-library:${scalaVersion}")
    compile("org.scala-lang:scala-reflect:${scalaVersion}")
    compile("org.scala-lang:scala-compiler:${scalaVersion}")
    compile("org.scalatest:scalatest_2.13.0-M2:${scalatestVersion}")
    compile("org.pegdown:pegdown:1.6.0")

}

application {
    mainClassName = "com.greatdreams.learn.scala13.MainProgram"
}


val sourcesJar by tasks.creating(Jar::class) {
    classifier = "sources"
    from(java.sourceSets["main"].allSource)
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
            }

        }
    }

    (publications) {
        "mavenJava"(MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar)
        }
    }
}
tasks {
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


    "test"(Test::class) {
        maxParallelForks = 1
        reports {
            junitXml.setEnabled(true)
            html.setEnabled(true)
        }
    }
}
