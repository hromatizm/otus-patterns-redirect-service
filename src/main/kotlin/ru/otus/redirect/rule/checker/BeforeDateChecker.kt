package ru.otus.redirect.rule.checker

import java.time.LocalDateTime

class BeforeDateChecker() : Checker() {

    override val code = "beforeDate"

    override fun check(args: Map<String, Any>): Boolean {
        val actual = getActualValue(args)
        val expected = getExpectedValue(args)
        return actual.isBefore(expected)
    }

    private fun getActualValue(args: Map<String, Any>): LocalDateTime {
        return LocalDateTime.now()
    }

    private fun getExpectedValue(args: Map<String, Any>): LocalDateTime? {
        val rule = args["rule"] as Map<*, *>
        return LocalDateTime.parse(rule["beforeDate"] as String)
    }
}