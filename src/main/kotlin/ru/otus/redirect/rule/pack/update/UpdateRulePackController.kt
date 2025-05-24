package ru.otus.redirect.rule.pack.update

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.redirect.rule.pack.RulePackDto
import ru.otus.redirect.rule.pack.toDto
import ru.otus.redirect.rule.pack.toModel
import util.lazyLogger

@RestController
@RequestMapping("/api/v1/rule-pack")
class UpdateRulePackController(
    private val updateRulePackCmd: UpdateRulePackCmd
) {

    private val logger by lazyLogger()

    @PutMapping("/update")
    fun update(@RequestBody dto: RulePackDto): ResponseEntity<RulePackDto> {
        logger.info("Update rule-pack request received: $dto")
        val updatedModel = updateRulePackCmd.execute(
            args = mapOf("model" to dto.toModel())
        )
        return ResponseEntity.ok().body(updatedModel.toDto())
    }

}
