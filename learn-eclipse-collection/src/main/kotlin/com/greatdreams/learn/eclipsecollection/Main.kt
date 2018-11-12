package com.greatdreams.learn.eclipsecollection

import org.eclipse.collections.impl.factory.Lists

data class Person(var firstName: String, var lastName: String)

fun main() {
    val person1 = Person("Sally", "Smith")
    val person2 = Person("Ted", "Watson")
    val person3 = Person("Mary", "Williams")

    val persons = Lists.immutable.with(person1, person2, person3)
    val lastNames = persons.collect { it.lastName }
    println(persons.size())
    println(lastNames.makeString())
}