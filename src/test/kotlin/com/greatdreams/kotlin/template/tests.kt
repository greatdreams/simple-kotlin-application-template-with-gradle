package com.greatdreams.kotlin.template

import com.winterbe.expekt.should
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object SimpleSpec: Spek({
            describe("a calculator") {
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