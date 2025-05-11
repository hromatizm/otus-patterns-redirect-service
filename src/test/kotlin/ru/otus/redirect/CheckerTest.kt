package ru.otus.redirect

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.otus.redirect.rule.checker.CheckerRegistry

class CheckerTest {

    @Test
    fun `get lang checker`() {
        val langChecker = CheckerRegistry.get("lang")
        val langChecker1 = CheckerRegistry.get("lang")

        assertThat(langChecker).isNotNull
    }
}