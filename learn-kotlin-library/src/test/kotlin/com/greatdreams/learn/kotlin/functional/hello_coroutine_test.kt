package com.greatdreams.learn.kotlin.functional

import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test

class HelloCoroutineTest {
    @Test
    fun test1() = runBlocking<Unit> {
        assert(100 == 100)
    }
}