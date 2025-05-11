package ru.otus.redirect.rule.checker

import org.springframework.stereotype.Service
import ru.otus.redirect.exception.RulePackAlreadyExistsException
import ru.otus.redirect.exception.RulePackNotExistsException
import ru.otus.redirect.rule.pack.RulePackModel
import ru.otus.redirect.rule.pack.RulePackService
import util.lazyLogger

@Service
class CheckerService(
    private val rulePackService: RulePackService
) {

    private val logger by lazyLogger()

//    fun getCheckerChain(uri: String): Checker {
//        logger.info("Get checker chain. Started: $uri")
//        val rulePack = rulePackService.get(uri)
//        rulePack.pack.forEach {
//            interpret(rule = it)
//        }
//    }

    private fun interpret(rule: Map<String, String>) {


    }

}
