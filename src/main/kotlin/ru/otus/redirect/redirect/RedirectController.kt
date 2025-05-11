package ru.otus.redirect.redirect

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.otus.redirect.rule.pack.RulePackDto
import ru.otus.redirect.rule.pack.RulePackService
import ru.otus.redirect.rule.pack.toDto
import util.lazyLogger
import java.net.URI

@RestController
@RequestMapping("/api/v1/")
class RedirectController(
    private val rulePackService: RulePackService
) {

    private val logger by lazyLogger()

    @GetMapping("/smart-link/{uri}")
    fun get(
        @PathVariable uri: String,
        @RequestHeader headers: Map<String, String>
    ): ResponseEntity<Unit> {
        logger.info("Smart-link request received: $uri")
        val rulePack = rulePackService.get(uri)
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create("https://example.com"))
            .build()
    }

}