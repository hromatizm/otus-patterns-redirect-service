package ru.otus.redirect.rule.checker

import java.time.LocalDateTime

class BeforeDateChecker(
    override val expectedValue: Any? = null,
) : Checker() {

    override val code = "beforeDate"

    override fun check(args: Map<String, Any>): Boolean {
        val actual = LocalDateTime.now()
        val expected = LocalDateTime.parse(expectedValue as String)
        return actual.isBefore(expected)
    }
}