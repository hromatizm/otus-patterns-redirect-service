package ru.otus.redirect.rule.pack.delete

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import util.lazyLogger

@RestController
@RequestMapping("/api/v1/rule-pack")
class DeletePackController(
    private val deleteRulePackCmd: DeleteRulePackCmd
) {

    private val logger by lazyLogger()

    @DeleteMapping("/delete")
    fun delete(@RequestParam uri: String): ResponseEntity<Unit> {
        logger.info("Delete rule-pack request received: $uri")
        deleteRulePackCmd.execute(
            args = mapOf("uri" to uri)
        )
        return ResponseEntity.ok().build()
    }
}