package ru.otus.redirect.rule.pack

import org.springframework.stereotype.Service
import ru.otus.redirect.exception.RulePackAlreadyExistsException
import ru.otus.redirect.exception.RulePackNotExistsException
import util.lazyLogger

@Service
class RulePackService(
    private val rulePackRepo: IRulePackRepository
) {

    private val logger by lazyLogger()

    fun create(dto: RulePackDto): RulePackDto {
        logger.info("Create rule pack. Started: $dto")
        if (rulePackRepo.existsByUrl(dto.url)) throw RulePackAlreadyExistsException(url = dto.url)
        val savedEntity = rulePackRepo.save(dto.toEntity())
        return savedEntity.toDto().also {
            logger.info("Create rule pack. Finished: $it")
        }
    }

    fun get(url: String): RulePackDto {
        logger.info("Get rule pack. Started: $url")
        val existingEntity = findByUrlOrElseThrow(url)
        return existingEntity.toDto().also {
            logger.info("Get rule pack. Finished: $it")
        }
    }

    fun update(dto: RulePackDto): RulePackDto {
        logger.info("Update rule pack. Started: $dto")
        val existingEntity = findByUrlOrElseThrow(dto.url)
        existingEntity.pack = dto.pack
        val savedEntity = rulePackRepo.save(existingEntity)
        return savedEntity.toDto().also {
            logger.info("Update rule pack. Finished: $it")
        }
    }

    fun delete(url: String) {
        logger.info("Delete rule pack. Started: $url")
        val existingEntity = findByUrlOrElseThrow(url)
        rulePackRepo.deleteById(existingEntity.id!!)
        logger.info("Delete rule pack. Finished. id: ${existingEntity.id}")
    }

    private fun findByUrlOrElseThrow(url: String): RulePackEntity {
        return rulePackRepo.findByUrl(url)
            ?: throw RulePackNotExistsException(url = url)
    }
}
