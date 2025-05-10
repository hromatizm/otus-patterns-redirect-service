package ru.otus.redirect.rule.pack

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
        val createdDto = rulePackService.create(dto)
        return ResponseEntity.ok().body(createdDto)
    }

    @PostMapping("/get")
    fun get(@RequestBody url: String): ResponseEntity<RulePackDto> {
        logger.info("Get rule-pack request received: $url")
        val dto = rulePackService.get(url)
        return ResponseEntity.ok().body(dto)
    }

    @PostMapping("/update")
    fun update(@RequestBody dto: RulePackDto): ResponseEntity<RulePackDto> {
        logger.info("Update rule-pack request received: $dto")
        val updatedDto = rulePackService.update(dto)
        return ResponseEntity.ok().body(updatedDto)
    }

    @PostMapping("/delete")
    fun delete(@RequestBody url: String): ResponseEntity<Unit> {
        logger.info("Delete rule-pack request received: $url")
        rulePackService.delete(url)
        return ResponseEntity.ok().build()
    }
}