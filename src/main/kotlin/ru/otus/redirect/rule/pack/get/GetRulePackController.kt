package ru.otus.redirect.rule.pack.get

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.otus.redirect.rule.pack.RulePackDto
import ru.otus.redirect.rule.pack.toDto
import util.lazyLogger

@RestController
@RequestMapping("/api/v1/rule-pack")
class GetRulePackController(
    private val getRulePackCmd: GetRulePackCmd
) {

    private val logger by lazyLogger()

    @GetMapping("/get")
    fun read(@RequestParam uri: String): ResponseEntity<RulePackDto> {
        logger.info("Get rule-pack request received: $uri")
        val model = getRulePackCmd.execute(
            args = mapOf("uri" to uri)
        )
        return ResponseEntity.ok().body(model.toDto())
    }

}