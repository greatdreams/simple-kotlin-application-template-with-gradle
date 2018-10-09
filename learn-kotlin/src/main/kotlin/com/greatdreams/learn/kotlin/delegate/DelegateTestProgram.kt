package com.greatdreams.learn.kotlin.delegate

import org.slf4j.LoggerFactory
import kotlin.reflect.KProperty

object DelegateTestProgram {
    @JvmStatic
    fun main(args: Array<String>) {
        println("In kotlin, learn delegate property")
        val example = Example()
        println(example.p)

        val b = BaseImpl(1)
        Derived(b).print()
        println()
        Derived(b).printMessage()
        println()
        Derived(b).printMessageLine()
    }
}

class Example {
    val p: String by Delegate()
}
class Delegate {
    operator fun getValue(example: Any?, property: KProperty<*>): String {
        return "$example, thank you for delegating '${property.name}' to me!"
    }
}

//Delegation

interface Base {
    val message: String
    fun print()
    fun printMessage()
    fun printMessageLine()
}

class BaseImpl(val x: Int) : Base {
    override val message: String = "BaseImpl: x = $x"

    override fun printMessageLine() {
        println(message)
    }

    override fun printMessage() {
        print(x)
    }

    override fun print() {
        print(x)
    }
}

class Derived(b: Base) : Base by b {
    override val message = "Message of Derived"

    override fun printMessage() {
        print("ABC")
    }
}

