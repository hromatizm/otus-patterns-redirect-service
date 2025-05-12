package ru.otus.redirect.redirect

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import util.lazyLogger
import java.net.URI

@RestController
@RequestMapping("/api/v1/")
class RedirectController(
    private val redirectService: RedirectService
) {

    private val logger by lazyLogger()

    @GetMapping("/smart-link/{uri}")
    fun get(
        @PathVariable uri: String,
        @RequestHeader headers: Map<String, String>
    ): ResponseEntity<String> {
        logger.info("Smart-link request received: $uri")
        val redirectLink = redirectService.getLink(uri = uri, args = mapOf("headers" to headers))
        return ResponseEntity.ok().body(redirectLink)
    }

}
