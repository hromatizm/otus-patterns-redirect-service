package ru.otus.redirect.rule.condition

import ru.otus.core.Condition
import java.time.LocalDate

class BeforeDateCondition(
    override val expectedValue: Any? = null,
) : Condition() {

    override val code = "beforeDate"

    override fun execute(args: Map<String, Any>): Boolean {
        val actual = LocalDate.now()
        val expected = LocalDate.parse(expectedValue as String)
        return actual.isBefore(expected)
    }
}