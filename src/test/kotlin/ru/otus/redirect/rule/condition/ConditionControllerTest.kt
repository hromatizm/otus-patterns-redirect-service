package ru.otus.redirect.rule.condition

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.io.File
import java.net.URI
import java.nio.file.Files

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ConditionControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Nested
    inner class UploadConditionJarSuccessfullyTest {

        val resource = this::class.java.classLoader.getResource("condition-after-date-OK.jar")
            ?: throw IllegalArgumentException("JAR not found in test resources")
        val file = File(resource.toURI())
        val jarBytes = Files.readAllBytes(file.toPath())
        val correctConditionJar = MockMultipartFile(
            "jar",
            "condition-after-date-OK.jar",
            "application/java-archive",
            jarBytes
        )
        private val uri = URI("/api/v1/condition-upload")

        @Test
        fun `the answer is OK`() {
            // Act
            val resultAction = mockMvc.perform(
                multipart(uri).file(correctConditionJar)
            )

            // Assert
            resultAction.andExpect(status().isOk)
        }

        @Test
        fun `condition is registered`() {
            // Pre-assert
            assertThat(ConditionRegistry.get("afterDate", "2021-01-01"))
                .withFailMessage("Condition should not be registered before upload")
                .isNull()

            // Act
            mockMvc.perform(
                multipart(uri).file(correctConditionJar)
            )

            // Assert
            assertThat(ConditionRegistry.get("afterDate", "2021-01-01"))
                .withFailMessage("Condition should be registered after upload")
                .isNotNull()
        }
    }

    @Nested
    inner class UploadConditionJarFailTest {

        val resource = this::class.java.classLoader.getResource("condition-after-date-FAIL.jar")
            ?: throw IllegalArgumentException("JAR not found in test resources")
        val file = File(resource.toURI())
        val jarBytes = Files.readAllBytes(file.toPath())
        val wrongConditionJar = MockMultipartFile(
            "jar",
            "condition-after-date-FAIL.jar",
            "application/java-archive",
            jarBytes
        )
        private val uri = URI("/api/v1/condition-upload")

        @Test
        fun `the answer is 400`() {
            // Act
            val resultAction = mockMvc.perform(
                multipart(uri).file(wrongConditionJar)
            )

            // Assert
            resultAction.andExpect(status().isBadRequest)
        }

        @Test
        fun `condition is no registered`() {

            // Act
            mockMvc.perform(
                multipart(uri).file(wrongConditionJar)
            )

            // Assert
            assertThat(ConditionRegistry.get("afterDate", "2021-01-01")).isNull()
        }
    }
}