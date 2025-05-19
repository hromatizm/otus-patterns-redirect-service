package ru.otus.redirect.rule.pack.delete

import org.springframework.stereotype.Component
import ru.otus.core.ICommand
import ru.otus.redirect.rule.pack.IRulePackRepository
import util.lazyLogger

@Component
class DeleteRulePackCmd(
    private val rulePackRepo: IRulePackRepository
) : ICommand<Unit> {

    private val logger by lazyLogger()

    override fun execute(args: Map<String, Any>) {
        val uri = args["uri"] as String
        logger.info("Delete rule pack. Started: $uri")
        val existingModel = rulePackRepo.findByUrlOrElseThrow(uri)
        rulePackRepo.deleteById(existingModel.id!!)
        logger.info("Delete rule pack. Finished. id: ${existingModel.id}")
    }
}
