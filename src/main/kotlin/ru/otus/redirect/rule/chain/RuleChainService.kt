package ru.otus.redirect.rule.chain

import org.springframework.stereotype.Service
import ru.otus.redirect.exception.EmptyRulePackException
import ru.otus.redirect.rule.checker.CheckerRegistry
import ru.otus.redirect.rule.pack.RulePackModel
import ru.otus.redirect.rule.pack.RulePackService
import util.lazyLogger

@Service
class RuleChainService(
    private val rulePackService: RulePackService
) {

    private val logger by lazyLogger()

    fun getRuleChain(uri: String): RuleChainModel {
        logger.info("Get checker chain. Started: $uri")
        val rulePack = rulePackService.get(uri)
        val ruleChain = interpret(rulePack) ?: throw EmptyRulePackException(uri)
        logger.info("Get checker chain. Finished: $uri")
        return ruleChain
    }

    private fun interpret(rulePack: RulePackModel): RuleChainModel? {
        val defaultUrl = rulePack.pack.firstNotNullOf { it.getValue("defaultUrl") }
        val ruleChain = rulePack.pack.foldRight(initial = null as RuleChainModel?) { rule, next ->
            RuleChainModel(
                checkers = rule.keys.mapNotNull { ruleCode -> CheckerRegistry.get(ruleCode) },
                redirectUrl = rule.getValue("redirect"),
                next = next,
                defaultUrl = defaultUrl
            )
        }
        return ruleChain
    }

}
