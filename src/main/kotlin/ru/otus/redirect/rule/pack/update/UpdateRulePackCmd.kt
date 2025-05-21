package ru.otus.redirect.rule.pack.update

import org.springframework.stereotype.Component
import ru.otus.core.ICommand
import ru.otus.redirect.rule.pack.IRulePackRepository
import ru.otus.redirect.rule.pack.RulePackModel
import util.lazyLogger

@Component
class UpdateRulePackCmd(
    private val rulePackRepo: IRulePackRepository
) : ICommand<RulePackModel> {

    private val logger by lazyLogger()

    override fun execute(args: Map<String, Any>): RulePackModel {
        val model = args["model"] as RulePackModel
        logger.info("Update rule pack. Started: $model")
        val existingModel = rulePackRepo.findByUriOrElseThrow(model.uri)
        val savedModel = rulePackRepo.save(
            existingModel.copy(pack = model.pack)
        )
        return savedModel.also {
            logger.info("Update rule pack. Finished: $it")
        }
    }
}
