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

    fun create(model: RulePackModel): RulePackModel {
        logger.info("Create rule pack. Started: $model")
        if (rulePackRepo.existsByUri(model.uri)) throw RulePackAlreadyExistsException(uri = model.uri)
        val savedModel = rulePackRepo.save(model)
        return savedModel.also {
            logger.info("Create rule pack. Finished: $it")
        }
    }

    fun get(uri: String): RulePackModel {
        logger.info("Get rule pack. Started: $uri")
        val existingModel = findByUrlOrElseThrow(uri)
        return existingModel.also {
            logger.info("Get rule pack. Finished: $it")
        }
    }

    fun update(model: RulePackModel): RulePackModel {
        logger.info("Update rule pack. Started: $model")
        val existingModel = findByUrlOrElseThrow(model.uri)
        val savedModel = rulePackRepo.save(
            existingModel.copy(pack = model.pack)
        )
        return savedModel.also {
            logger.info("Update rule pack. Finished: $it")
        }
    }

    fun delete(uri: String) {
        logger.info("Delete rule pack. Started: $uri")
        val existingModel = findByUrlOrElseThrow(uri)
        rulePackRepo.deleteById(existingModel.id!!)
        logger.info("Delete rule pack. Finished. id: ${existingModel.id}")
    }

    private fun findByUrlOrElseThrow(url: String): RulePackModel {
        return rulePackRepo.findByUrl(url)
            ?: throw RulePackNotExistsException(uri = url)
    }
}
