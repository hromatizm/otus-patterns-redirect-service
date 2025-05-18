package ru.otus.redirect.rule.checker

class LangChecker(
    override val expectedValue: Any? = null,
) : Checker() {

    override val code = "lang"

    override fun check(args: Map<String, Any>): Boolean {
        val actual = args["accept-language"] as String?
        val expected = expectedValue as String
        return actual == expected
    }

}
