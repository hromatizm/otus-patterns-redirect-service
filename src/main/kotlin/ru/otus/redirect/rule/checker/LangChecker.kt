package ru.otus.redirect.rule.checker

class LangChecker() : Checker() {

    override val code = "lang"

    override fun check(args: Map<String, Any>): Boolean {
        val actual = getActualValue(args)
        val expected = getExpectedValue(args)
        return actual == expected
    }

    private fun getActualValue(args: Map<String, Any>): String? {
        val headers = args["headers"] as Map<*, *>
        val header = headers["Accept-Language"] as String
        return header.split(",")[0]
    }

    private fun getExpectedValue(args: Map<String, Any>): String? {
        val rule = args["rule"] as Map<*, *>
        return rule["lang"] as String?
    }
}