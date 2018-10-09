package com.greatdreams.learn.kotlin.functional

import arrow.core.Option
import arrow.core.Some
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import kotlin.test.assertEquals

class SimpleArrowTest: Spek({
    describe("arrow optional object is test") {
        it ("should be not empty") {
            val someValue: Option<String> = Some("I am wrapped in something")
           assertEquals(false, someValue.isEmpty())
        }
    }

})