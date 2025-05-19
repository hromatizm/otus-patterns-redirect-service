package ru.otus.redirect.rule.chain

import org.springframework.stereotype.Service
import ru.otus.core.ICommand
import ru.otus.redirect.exception.ConditionNotFoundException
import ru.otus.redirect.exception.EmptyRulePackException
import ru.otus.redirect.rule.condition.ConditionRegistry
import ru.otus.redirect.rule.pack.RulePackModel
import ru.otus.redirect.rule.pack.get.GetRulePackCmd
import util.lazyLogger

@Service
class GetRuleChainCmd(
    private val getRulePackCmd: GetRulePackCmd
) : ICommand<ICommand<String?>?> {

    private val logger by lazyLogger()

    override fun execute(args: Map<String, Any>): ICommand<String?>? {
        val uri = args["uri"] as String
        logger.info("Get rule chain. Started: $args")
        val rulePack = getRulePackCmd.execute(
            args = mapOf("uri" to uri)
        )
        val ruleChain = interpret(rulePack) ?: throw EmptyRulePackException(uri)
        logger.info("Get rule chain. Finished: $uri")
        return ruleChain
    }

    private fun interpret(rulePack: RulePackModel): ICommand<String?>? {
        val defaultUrl = rulePack.pack.firstNotNullOfOrNull { it.getOrDefault("defaultUrl", null) }
        val ruleChainCmd = rulePack.pack
            .filter { it.containsKey("redirect") }
            .foldRight(initial = null as RuleChainCmd?) { rule, next ->
                RuleChainCmd(
                    conditions = rule.mapNotNull {
                        if (it.key == "redirect") return@mapNotNull null
                        ConditionRegistry.get(code = it.key, expectedValue = it.value)
                            ?: throw ConditionNotFoundException(code = it.key)
                    },
                    redirectUrl = rule.getValue("redirect"),
                    next = next,
                    defaultUrl = defaultUrl
                )
            }
        logger.info("Get rule chain: $ruleChainCmd")
        return ruleChainCmd
    }

}
