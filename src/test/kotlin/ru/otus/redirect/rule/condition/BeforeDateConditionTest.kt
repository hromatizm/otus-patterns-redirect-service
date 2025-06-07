package ru.otus.redirect.rule.condition

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BeforeDateConditionTest {

    @Test
    fun `check condition - true`() {
        // Arrange
        val condition = BeforeDateCondition(expectedValue = "2030-01-01")

        // Act
        val result = condition.execute()

        // Assert
        assertThat(result).isTrue
    }

    @Test
    fun `check condition - false`() {
        // Arrange
        val condition = BeforeDateCondition(expectedValue = "2010-01-01")

        // Act
        val result = condition.execute()

        // Assert
        assertThat(result).isFalse
    }

}