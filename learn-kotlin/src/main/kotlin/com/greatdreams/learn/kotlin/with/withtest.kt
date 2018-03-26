package com.greatdreams.learn.kotlin.with

fun alphabet(): String {
    val stringBuffer = StringBuffer()
    return with(stringBuffer) {
        for(letter in 'A'..'Z') {
            this.append(letter)
        }
        append("\nNow  I konw the aphabet")
        this.toString()
    }
}

fun alphabetV1() = with(StringBuffer()) {
    for(letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow  I konw the aphabet")
    toString()
}
fun alphabetV2() = StringBuffer().apply {
    for(letter in 'A'.. 'Z') {
        append(letter)
    }
    append("\nNow  I konw the aphabet")
}.toString()

fun buildString(build: StringBuilder.() -> Unit): String {
    val stringBuilder = StringBuilder()
    stringBuilder.build()
    return stringBuilder.toString()
}

fun alphabetV3() = buildString {
    for(letter in 'A'..'Z') {
        append(letter)
    }
    append("\nNow  I konw the aphabet")
}

object WithTest {
    @JvmStatic fun main(args: Array<String>)  {
        println(alphabet())
        println(alphabetV1())
        println(alphabetV2())
        println(alphabetV3())
    }
}