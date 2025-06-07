package ru.otus.redirect.rule.pack.get

import org.springframework.stereotype.Component
import ru.otus.core.ICommand
import ru.otus.redirect.rule.pack.IRulePackRepository
import ru.otus.redirect.rule.pack.RulePackModel
import util.lazyLogger

@Component
class GetRulePackCmd(
    private val rulePackRepo: IRulePackRepository
) : ICommand<RulePackModel> {

    private val logger by lazyLogger()

    override fun execute(args: Map<String, Any>): RulePackModel {
        val uri = args["uri"] as String
        logger.info("Get rule pack. Started: $uri")
        val existingModel = rulePackRepo.findByUriOrElseThrow(uri)
        return existingModel.also {
            logger.info("Get rule pack. Finished: $it")
        }
    }
}
