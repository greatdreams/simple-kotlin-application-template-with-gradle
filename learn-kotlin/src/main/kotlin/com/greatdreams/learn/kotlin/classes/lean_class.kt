package com.greatdreams.learn.kotlin.classes

import java.util.*

object LearnClasses {
    @JvmStatic fun main(args: Array<String>) {
        val initOrderDemo = InitOrderDemo("Hello, Kotlin")
        val customer = Customer("Jendis")
        println(customer.customerKey)
        println(Customer().customerKey)
        val constructors = Constructors(1)
    }
}

class InitOrderDemo(name: String) {
    val firstProperty = "First property: $name".also(::println)

    init {
        println("First initializer block that prints ${name}")
    }

    val secondProperty = "Second property: ${name.length}".also(::println)

    init {
        println("Second initializer block that prints ${name.length}")
    }
}

class Customer(name: String = "") {
    val customerKey = name.toUpperCase()
}

class Person(val name: String) {
    val children = LinkedList<Person>()
    constructor(name: String, parent: Person) : this(name) {
        parent.children.add(this)
    }
}

class Constructors {
    init {
        println("Init block")
    }

    constructor(i: Int) {
        println("Constructor")
    }
}