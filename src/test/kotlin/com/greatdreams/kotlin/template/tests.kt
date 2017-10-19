package com.greatdreams.kotlin.template

import com.winterbe.expekt.should
import junit.framework.Assert.assertEquals
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*

object SimpleSpec: Spek({
            xdescribe("a calculator") {
                on("addition" ) {
                    val sum = 100 + 200
                    it("should work") {
                        300.should.equal(sum)
                    }
                }

                on("minus" ) {
                    val result = 100 - 200
                    it("should work") {
                        (-100).should.equal(result)
                    }
                }
            }
        }
)

object CalculatorSpec: Spek({
    given("a calculator") {
        val num = 100
        val num1 = 200
        on("addition") {
            val sum = num + num1
            it("should return the result of adding the first number to the second number") {
                assertEquals(300, sum)
            }
        }
        on("subtraction") {
            val result = num - num1
            xit("should return the result of substracting the second number from the first") {
                assertEquals(-100, result)
            }
        }
    }
})

object FixtureSpec: Spek({
    describe("a group") {
        beforeGroup {

        }
        beforeEachTest {

        }

        context("a nest group") {
            beforeEachTest {

            }
            beforeEachTest {

            }
            it("should work") {

            }
            afterEachTest {

            }
        }
        afterEachTest {

        }

        afterGroup {

        }
    }
})