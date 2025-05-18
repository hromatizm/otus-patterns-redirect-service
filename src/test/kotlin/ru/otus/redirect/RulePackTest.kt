package ru.otus.redirect

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.redirect.rule.pack.IRulePackJpaRepository
import ru.otus.redirect.rule.pack.RulePackDto
import ru.otus.redirect.rule.pack.RulePackEntity
import java.net.URI

private const val INITIAL_URI = "/patterns-course"
private const val PACK_ID = 1234567890L

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RulePackTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockitoBean
    lateinit var rulePackJpaRepository: IRulePackJpaRepository

    private val pack = listOf(
        mapOf(
            "lang" to "ru",
            "name" to "test_name_1",
            "redirect" to "https://otus.ru/test_redirect_1"
        ),
        mapOf(
            "lang" to "en",
            "name" to "test_name_2",
            "redirect" to "https://otus.ru/test_redirect_2"
        )
    )

    @Nested
    inner class CreateTest {

        private val uri = URI("/api/v1/rule-pack/create")

        private val rulePackToSave = RulePackDto(
            uri = INITIAL_URI,
            pack = pack
        )

        @BeforeEach
        fun setupMock() {
            whenever(
                rulePackJpaRepository.save(any())
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
            whenever(
                rulePackJpaRepository.existsByUri(INITIAL_URI)
            ).thenReturn(false)
            val request = preparePostRequest(uri = uri, body = rulePackToSave)

            // Act
            val resultActions: ResultActions = mockMvc.perform(request)

            // Assert
            resultActions.andExpect(status().isOk)
        }

        @Test
        fun `body with pack`() {
            // Arrange
            whenever(
                rulePackJpaRepository.existsByUri(INITIAL_URI)
            ).thenReturn(false)
            val targetDto = rulePackToSave.copy(id = PACK_ID)
            val request = preparePostRequest(uri = uri, body = rulePackToSave)

            // Act
            val content = mockMvc
                .perform(request)
                .andReturn().response.contentAsString
            val resultDto = objectMapper.readValue(content, RulePackDto::class.java)

            // Assert
            assertThat(resultDto).isEqualTo(targetDto)
        }

        @Test
        fun `rule-pack already exists, the answer is 409`() {
            // Arrange
            whenever(
                rulePackJpaRepository.existsByUri(INITIAL_URI)
            ).thenReturn(true)
            val request = preparePostRequest(uri = uri, body = rulePackToSave)

            // Act
            val resultActions: ResultActions = mockMvc.perform(request)

            // Assert
            resultActions.andExpect(status().isConflict)
        }

        @Test
        fun `rule-pack already exists, the body with error`() {
            // Arrange
            whenever(
                rulePackJpaRepository.existsByUri(INITIAL_URI)
            ).thenReturn(true)
            val request = preparePostRequest(uri = uri, body = rulePackToSave)

            // Act
            val content = mockMvc
                .perform(request)
                .andReturn().response.contentAsString

            // Assert
            assertThat(content).isEqualTo("Rule pack for uri: $INITIAL_URI already exists")
        }

    }

    @Nested
    inner class GetTest {

        private val uri = URI("/api/v1/rule-pack/get")

        @Test
        fun `the answer is OK`() {
            // Arrange
            whenever(
                rulePackJpaRepository.findByUri(INITIAL_URI)
            ).thenReturn(
                RulePackEntity(
                    id = PACK_ID,
                    uri = INITIAL_URI,
                    pack = pack
                )
            )
            val request = prepareGetRequest(uri = uri, param = INITIAL_URI)

            // Act
            val resultActions: ResultActions = mockMvc.perform(request)

            // Assert
            resultActions.andExpect(status().isOk)
        }

        @Test
        fun `body with pack`() {
            // Arrange
            whenever(
                rulePackJpaRepository.findByUri(INITIAL_URI)
            ).thenReturn(
                RulePackEntity(
                    id = PACK_ID,
                    uri = INITIAL_URI,
                    pack = pack
                )
            )
            val request = prepareGetRequest(uri = uri, param = INITIAL_URI)
            val targetDto = RulePackDto(
                id = PACK_ID,
                uri = INITIAL_URI,
                pack = pack
            )

            // Act
            val content = mockMvc
                .perform(request)
                .andReturn().response.contentAsString
            val resultDto = objectMapper.readValue(content, RulePackDto::class.java)

            // Assert
            assertThat(resultDto).isEqualTo(targetDto)
        }

        @Test
        fun `rule-pack not exists, the answer is 404`() {
            // Arrange
            whenever(
                rulePackJpaRepository.findByUri(INITIAL_URI)
            ).thenReturn(null)
            val request = prepareGetRequest(uri = uri, param = INITIAL_URI)

            // Act
            val resultActions: ResultActions = mockMvc.perform(request)

            // Assert
            resultActions.andExpect(status().isNotFound)
        }

        @Test
        fun `rule-pack not exists, the body with error`() {
            // Arrange
            whenever(
                rulePackJpaRepository.findByUri(INITIAL_URI)
            ).thenReturn(null)
            val request = prepareGetRequest(uri = uri, param = INITIAL_URI)

            // Act
            val content = mockMvc
                .perform(request)
                .andReturn().response.contentAsString

            // Assert
            assertThat(content).isEqualTo("Rule pack for uri: $INITIAL_URI not exists")
        }

    }

    @Nested
    inner class UpdateTest {

        private val uri = URI("/api/v1/rule-pack/update")

        private val rulePackToUpdate = RulePackDto(
            uri = INITIAL_URI,
            pack = pack
        )

        @BeforeEach
        fun setupMock() {
            whenever(
                rulePackJpaRepository.save(any())
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
            whenever(
                rulePackJpaRepository.findByUri(INITIAL_URI)
            ).thenReturn(RulePackEntity(id = PACK_ID))
            val request = preparePutRequest(uri = uri, body = rulePackToUpdate)

            // Act
            val resultActions: ResultActions = mockMvc.perform(request)

            // Assert
            resultActions.andExpect(status().isOk)
        }

        @Test
        fun `body with pack`() {
            // Arrange
            whenever(
                rulePackJpaRepository.findByUri(INITIAL_URI)
            ).thenReturn(RulePackEntity(id = PACK_ID))
            val targetDto = rulePackToUpdate.copy(id = PACK_ID)
            val request = preparePutRequest(uri = uri, body = rulePackToUpdate)

            // Act
            val content = mockMvc
                .perform(request)
                .andReturn().response.contentAsString
            val resultDto = objectMapper.readValue(content, RulePackDto::class.java)

            // Assert
            assertThat(resultDto).isEqualTo(targetDto)
        }

        @Test
        fun `rule-pack not exists, the answer is 404`() {
            // Arrange
            whenever(
                rulePackJpaRepository.findByUri(INITIAL_URI)
            ).thenReturn(null)
            val request = preparePutRequest(uri = uri, body = rulePackToUpdate)

            // Act
            val resultActions: ResultActions = mockMvc.perform(request)

            // Assert
            resultActions.andExpect(status().isNotFound)
        }

        @Test
        fun `rule-pack already exists, the body with error`() {
            // Arrange
            whenever(
                rulePackJpaRepository.findByUri(INITIAL_URI)
            ).thenReturn(null)
            val request = preparePutRequest(uri = uri, body = rulePackToUpdate)

            // Act
            val content = mockMvc
                .perform(request)
                .andReturn().response.contentAsString

            // Assert
            assertThat(content).isEqualTo("Rule pack for uri: $INITIAL_URI not exists")
        }

    }

    @Nested
    inner class DeleteTest {

        private val uri = URI("/api/v1/rule-pack/delete")

        @Test
        fun `the answer is OK`() {
            // Arrange
            whenever(
                rulePackJpaRepository.findByUri(INITIAL_URI)
            ).thenReturn(
                RulePackEntity(
                    id = PACK_ID,
                    uri = INITIAL_URI,
                    pack = pack
                )
            )
            val request = prepareDeleteRequest(uri = uri, param = INITIAL_URI)

            // Act
            val resultActions: ResultActions = mockMvc.perform(request)

            // Assert
            resultActions.andExpect(status().isOk)
        }

        @Test
        fun `rule-pack not exists, the answer is 404`() {
            // Arrange
            whenever(
                rulePackJpaRepository.findByUri(INITIAL_URI)
            ).thenReturn(null)
            val request = prepareDeleteRequest(uri = uri, param = INITIAL_URI)

            // Act
            val resultActions: ResultActions = mockMvc.perform(request)

            // Assert
            resultActions.andExpect(status().isNotFound)
        }

        @Test
        fun `rule-pack not exists, the body with error`() {
            // Arrange
            whenever(
                rulePackJpaRepository.findByUri(INITIAL_URI)
            ).thenReturn(null)
            val request = prepareDeleteRequest(uri = uri, param = INITIAL_URI)

            // Act
            val content = mockMvc
                .perform(request)
                .andReturn().response.contentAsString

            // Assert
            assertThat(content).isEqualTo("Rule pack for uri: $INITIAL_URI not exists")
        }

    }

    private fun preparePostRequest(uri: URI, body: Any): MockHttpServletRequestBuilder {
        val messageJson = objectMapper.writeValueAsString(body)
        return post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(messageJson)
    }

    private fun prepareGetRequest(uri: URI, param: String): MockHttpServletRequestBuilder {
        return get(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .param("uri", param)
    }

    private fun preparePutRequest(uri: URI, body: Any): MockHttpServletRequestBuilder {
        val messageJson = objectMapper.writeValueAsString(body)
        return put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(messageJson)
    }

    private fun prepareDeleteRequest(uri: URI, param: String): MockHttpServletRequestBuilder {
        return delete(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .param("uri", param)
    }
}