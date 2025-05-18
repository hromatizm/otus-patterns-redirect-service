package ru.otus.redirect.rule.pack

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import util.lazyLogger

@RestController
@RequestMapping("/api/v1/rule-pack")
class RulePackController(
    private val rulePackService: RulePackService
) {

    private val logger by lazyLogger()

    @PostMapping("/create")
    fun create(@RequestBody dto: RulePackDto): ResponseEntity<RulePackDto> {
        logger.info("Create rule-pack request received: $dto")
        val createdModel = rulePackService.create(dto.toModel())
        return ResponseEntity.ok().body(createdModel.toDto())
    }

    @GetMapping("/get")
    fun get(@RequestParam uri: String): ResponseEntity<RulePackDto> {
        logger.info("Get rule-pack request received: $uri")
        val model = rulePackService.get(uri)
        return ResponseEntity.ok().body(model.toDto())
    }

    @PutMapping("/update")
    fun update(@RequestBody dto: RulePackDto): ResponseEntity<RulePackDto> {
        logger.info("Update rule-pack request received: $dto")
        val updatedModel = rulePackService.update(dto.toModel())
        return ResponseEntity.ok().body(updatedModel.toDto())
    }

    @DeleteMapping("/delete")
    fun delete(@RequestParam uri: String): ResponseEntity<Unit> {
        logger.info("Delete rule-pack request received: $uri")
        rulePackService.delete(uri)
        return ResponseEntity.ok().build()
    }
}