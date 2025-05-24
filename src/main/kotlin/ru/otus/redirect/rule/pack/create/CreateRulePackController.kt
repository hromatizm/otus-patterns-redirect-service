package ru.otus.redirect.rule.pack.create

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.redirect.rule.pack.RulePackDto
import ru.otus.redirect.rule.pack.toDto
import ru.otus.redirect.rule.pack.toModel
import util.lazyLogger

@RestController
@RequestMapping("/api/v1/rule-pack")
class CreateRulePackController(
    private val createRulePackCmd: CreateRulePackCmd,
) {

    private val logger by lazyLogger()

    @PostMapping("/create")
    fun create(@RequestBody dto: RulePackDto): ResponseEntity<RulePackDto> {
        logger.info("Create rule-pack request received: $dto")
        val createdModel = createRulePackCmd.execute(
            args = mapOf("model" to dto.toModel())
        )
        return ResponseEntity.ok().body(createdModel.toDto())
    }
}