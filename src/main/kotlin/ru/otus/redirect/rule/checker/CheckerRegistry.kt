package ru.otus.redirect.rule.checker

import ru.otus.redirect.exception.CheckerNotExistsException

class CheckerRegistry {

    companion object {

        private val checkers: MutableMap<String, Checker> = mutableMapOf(
            "lang" to LangChecker(),
            "userId" to BeforeDateChecker()
        )

        fun register(checker: Checker) {
            checkers[checker.code] = checker
        }

        fun get(code: String): Checker {
            val checker = checkers[code] ?: throw CheckerNotExistsException(code = code)
            return checker.copy()
        }
    }

}