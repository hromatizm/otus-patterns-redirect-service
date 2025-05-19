package ru.otus.redirect.redirect

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.otus.core.ICommand
import util.lazyLogger

@RestController
@RequestMapping("/api/v1")
class RedirectController(
    private val getRedirectLinkCmd: GetRedirectLinkCmd
) {

    private val logger by lazyLogger()

    @PostMapping("/smart-link/{uri}")
    fun getRedirectLink(
        @PathVariable uri: String,
        @RequestBody args: Map<String, String>
    ): ResponseEntity<String> {
        logger.info("Smart-link request received: $uri")
        val redirectLink = getRedirectLinkCmd.execute(args = args + ("uri" to uri))
        return ResponseEntity.ok().body(redirectLink)
    }

}
