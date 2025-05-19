package ru.otus.redirect.rule.condition

import ru.otus.core.Condition

class LangCondition(
    override val expectedValue: Any? = null,
) : Condition() {

    override val code = "lang"

    override fun execute(args: Map<String, Any>): Boolean {
        val actual = args["accept-language"] as String?
        val expected = expectedValue as String
        return actual == expected
    }

}
