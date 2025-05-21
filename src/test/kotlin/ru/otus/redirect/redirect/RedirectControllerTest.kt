package ru.otus.redirect.redirect

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.redirect.rule.pack.IRulePackJpaRepository
import ru.otus.redirect.rule.pack.RulePackEntity

private const val INITIAL_URI = "patterns-course"
private const val PACK_ID = 1234567890L

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RedirectControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockitoBean
    lateinit var rulePackJpaRepository: IRulePackJpaRepository

    private val pack = listOf(
        mapOf(
            "lang" to "ru",
            "beforeDate" to "2030-01-01",
            "redirect" to "https://redirect.ru"
        ),
        mapOf(
            "lang" to "en",
            "beforeDate" to "2030-01-01",
            "redirect" to "https://redirect.en"
        ),
        mapOf(
            "defaultUrl" to "https://default.url"
        )
    )

    @BeforeEach
    fun setupMock() {
        whenever(
            rulePackJpaRepository.findByUri(INITIAL_URI)
        ).thenReturn(
            RulePackEntity(
                id = PACK_ID,
                uri = INITIAL_URI,
                pack = pack
            )
        )
    }

    @Test
    fun `the answer is OK`() {
        // Arrange
        val args = mapOf("accept-language" to "ru")
        val request = prepareRedirectRequest(args)

        // Act
        val resultActions = mockMvc.perform(request)

        // Assert
        resultActions.andExpect(status().isOk)
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "ru, https://redirect.ru",
            "en, https://redirect.en",
            "ch, https://default.url",
        ]
    )
    fun `body with redirect to expected url`(lang: String, expectedUrl: String) {
        // Arrange
        val args = mapOf("accept-language" to lang)
        val request = prepareRedirectRequest(args)

        // Act
        val content = mockMvc
            .perform(request)
            .andReturn().response.contentAsString

        // Assert
        assertThat(content).isEqualTo(expectedUrl)
    }

    private fun prepareRedirectRequest(body: Any): MockHttpServletRequestBuilder {
        val messageJson = objectMapper.writeValueAsString(body)
        return post("/api/v1/smart-link/$INITIAL_URI")
            .contentType(MediaType.APPLICATION_JSON)
            .content(messageJson)
    }
}