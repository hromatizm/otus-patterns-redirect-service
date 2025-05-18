package ru.otus.redirect.rule.checker

import ru.otus.checker.Checker
import java.time.LocalDate

class BeforeDateChecker(
    override val expectedValue: Any? = null,
) : Checker() {

    override val code = "beforeDate"

    override fun check(args: Map<String, Any>): Boolean {
        val actual = LocalDate.now()
        val expected = LocalDate.parse(expectedValue as String)
        return actual.isBefore(expected)
    }
}