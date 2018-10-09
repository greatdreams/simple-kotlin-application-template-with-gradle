package com.greatdreams.learn.kotlin.classes

object LearnClasses1 {
    @JvmStatic fun main(args: Array<String>) {
        val derived = Derived(100)
        println(derived)
        println(derived.nv())
        val base: Base = Derived(1000)
        println(derived.v())
    }
}

open class Base(p: Int) {
    open fun v() {
        println("v - Base class")
    }
    open fun nv() {
        println("nv - Base class")
    }
}

class Derived(p: Int) : Base(p) {
    override fun v() {
        println("v - Derived class")
    }
    override fun nv() {
        println("nv - Derived class")
    }
}

interface Foo {
    val count: Int
}

class Bar1(override val count: Int) : Foo

class Bar2 : Foo {
    override var count: Int = 0
}