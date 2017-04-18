package com.greatdreams.kotlin.template

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*

/**
 * Created by greatdreams on 4/18/17.
 */

object Fixture: Spek({
    var testNumber: Int = 0
    describe("a group") {
        beforeGroup {
        }
        beforeEachTest {
        }
        context("a nested group") {
            beforeEachTest { }
            beforeEachTest { }

            it("should work") {

            }
        }
        it("do something") {

        }
        afterEachTest {  }
        afterGroup {  }
    }
})