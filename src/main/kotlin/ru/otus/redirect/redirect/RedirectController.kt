package ru.otus.redirect.redirect

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import util.lazyLogger

@RestController
@RequestMapping("/api/v1")
class RedirectController(
    private val redirectService: RedirectService
) {

    private val logger by lazyLogger()

    @PostMapping("/smart-link/{uri}")
    fun getRedirectLink(
        @PathVariable uri: String,
        @RequestBody args: Map<String, String>
    ): ResponseEntity<String> {
        logger.info("Smart-link request received: $uri")
        val redirectLink = redirectService.getLink(uri = uri, args = args)
        return ResponseEntity.ok().body(redirectLink)
    }

}
