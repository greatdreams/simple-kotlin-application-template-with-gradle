[gradle-scalatest](https://github.com/maiflai/gradle-scalatest)


A plugin to enable the use of scalatest in a gradle Scala project.

```gradle

plugins {
  id "com.github.maiflai.scalatest" version "0.22"
}

test {
    reports {
        junitXml.enabled = false
        html.enabled = true
    }               
}

```

##### Reference
1. [Introduction to Internal DSLs in Scala](http://www.devx.com/enterprise/introduction-to-internal-dsls-in-scala.html)
2. [IMPLICIT CLASSES](https://docs.scala-lang.org/overviews/core/implicit-classes.html)
3. [IMPLICIT PARAMETERS](http://docs.scala-lang.org/tour/implicit-parameters.html)
4. [scalatest gradle plugin](https://plugins.gradle.org/plugin/com.github.maiflai.scalatest)
5. [scala github](https://github.com/scalatest/scalatest)