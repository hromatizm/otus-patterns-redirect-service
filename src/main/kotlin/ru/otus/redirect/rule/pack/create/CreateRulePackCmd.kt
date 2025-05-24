package ru.otus.redirect.rule.pack.create

import org.springframework.stereotype.Component
import ru.otus.core.ICommand
import ru.otus.redirect.exception.RulePackAlreadyExistsException
import ru.otus.redirect.rule.pack.IRulePackRepository
import ru.otus.redirect.rule.pack.RulePackModel
import util.lazyLogger

@Component
class CreateRulePackCmd(
    private val rulePackRepo: IRulePackRepository
) : ICommand<RulePackModel> {

    private val logger by lazyLogger()

    override fun execute(args: Map<String, Any>): RulePackModel {
        val model = args["model"] as RulePackModel
        logger.info("Create rule pack. Started: $model")
        if (rulePackRepo.existsByUri(model.uri)) throw RulePackAlreadyExistsException(uri = model.uri)
        val savedModel = rulePackRepo.save(model)
        return savedModel.also {
            logger.info("Create rule pack. Finished: $it")
        }
    }
}
