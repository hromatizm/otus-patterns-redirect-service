package ru.otus.redirect.rule.condition

import ru.otus.core.Condition


class ConditionRegistry {

    companion object {

        private val conditions: MutableMap<String, Condition> = mutableMapOf(
            "lang" to LangCondition(),
            "beforeDate" to BeforeDateCondition()
        )

        fun register(condition: Condition) {
            conditions[condition.code] = condition
        }

        fun get(code: String, expectedValue: Any): Condition? {
            val condition = conditions[code]
            return condition?.getInstance(expectedValue)
        }
    }

}