package ru.otus.redirect

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.otus.redirect.rule.condition.ConditionRegistry

class ConditionTest {

    @Test
    fun `get lang condition`() {
        val langCondition1 = ConditionRegistry.get("lang", "ru")
        val langCondition2 = ConditionRegistry.get("lang", "en")

        assertThat(langCondition1).isNotNull
    }
}