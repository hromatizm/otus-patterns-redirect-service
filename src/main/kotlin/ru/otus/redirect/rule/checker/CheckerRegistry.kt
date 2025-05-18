package ru.otus.redirect.rule.checker

import ru.otus.checker.Checker

class CheckerRegistry {

    companion object {

        private val checkers: MutableMap<String, Checker> = mutableMapOf(
            "lang" to LangChecker(),
            "beforeDate" to BeforeDateChecker()
        )

        fun register(checker: Checker) {
            checkers[checker.code] = checker
        }

        fun get(code: String, expectedValue: Any): Checker? {
            val checker = checkers[code]
            return checker?.getInstance(expectedValue)
        }
    }

}