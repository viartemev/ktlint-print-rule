package com.viatemev.ktlint

import com.github.shyiko.ktlint.core.LintError
import com.github.shyiko.ktlint.test.lint
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it

class NoPrintRuleTest : Spek({

    describe("no-print rule") {

        // whenever KTLINT_DEBUG env variable is set to "ast" or -DktlintDebug=ast is used
        // com.github.shyiko.ktlint.test.(lint|format) will print AST (along with other debug info) to the stderr.
        // this can be extremely helpful while writing and testing rules.
        // uncomment the line below to take a quick look at it
        //System.setProperty("ktlintDebug", "ast")

        val rule = NoPrintRule()

        it("should prohibit usage of println") {
            assertThat(rule.lint("""fun fn() { println("Hello world") }"""))
                    .isEqualTo(listOf(LintError(1,
                            12,
                            "no-print",
                            "Do not use \"println\" in code, use logger instead")))
        }

        it("should prohibit usage of print") {
            assertThat(rule.lint("""fun fn() { print("Hello world") }"""))
                    .isEqualTo(listOf(LintError(1,
                            12,
                            "no-print",
                            "Do not use \"print\" in code, use logger instead")))
        }

        it("should prohibit usage of System.out.println") {
            assertThat(rule.lint("""fun fn() { System.out.println("Hello world") }"""))
                    .isEqualTo(listOf(LintError(1,
                            23,
                            "no-print",
                            "Do not use \"println\" in code, use logger instead")))
        }

        it("should prohibit usage of System.out.print") {
            assertThat(rule.lint("""fun fn() { System.out.print("Hello world") }"""))
                    .isEqualTo(listOf(LintError(1,
                            23,
                            "no-print",
                            "Do not use \"print\" in code, use logger instead")))
        }

        it("should prohibit usage of println in lambda expressions") {
            assertThat(rule.lint("""fun fn(list) { list.forEach( { println("Hello," + it) } ) }"""))
                    .isEqualTo(listOf(LintError(1,
                            32,
                            "no-print",
                            "Do not use \"println\" in code, use logger instead")))
        }


        it("should not prohibit usage of println as string") {
            assertThat(rule.lint("""fun fn() { LOGGER.log("println") }"""))
                    .isEqualTo(emptyList<LintError>())
        }

        it("should not prohibit usage of print as string") {
            assertThat(rule.lint("""fun fn() { LOGGER.log("print") }"""))
                    .isEqualTo(emptyList<LintError>())
        }

    }
})
