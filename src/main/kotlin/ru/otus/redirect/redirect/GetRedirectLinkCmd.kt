package ru.otus.redirect.redirect

import org.springframework.stereotype.Service
import ru.otus.core.ICommand
import ru.otus.redirect.rule.chain.GetRuleChainCmd
import util.lazyLogger

@Service
class GetRedirectLinkCmd(
    private val getRuleChainCmd: GetRuleChainCmd,
): ICommand<String?> {

    private val logger by lazyLogger()

   override fun execute(args: Map<String, Any>): String? {
        logger.info("Get Link. Started: $args")
        val ruleChain = getRuleChainCmd.execute(args)
        return ruleChain?.execute(args)
    }

}
