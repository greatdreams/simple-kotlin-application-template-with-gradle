package com.greatdreams.learn.kotlin.classes1

object LearnClasses2 {
    @JvmStatic fun main(args: Array<String>) {
    }
}

open class Base {
    open fun f() {
    }
}

abstract class Derived : Base() {
    override abstract fun f()
}

