package ru.otus.redirect.rule.condition

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LangConditionTest {

    @Test
    fun `check condition - true`() {
        // Arrange
        val condition = LangCondition(expectedValue = "ru")
        val args = mapOf("accept-language" to "ru")

        // Act
        val result = condition.execute(args)

        // Assert
        assertThat(result).isTrue
    }

    @Test
    fun `check condition - false`() {
        // Arrange
        val condition = LangCondition(expectedValue = "en")
        val args = mapOf("accept-language" to "ru")

        // Act
        val result = condition.execute(args)

        // Assert
        assertThat(result).isFalse
    }

}