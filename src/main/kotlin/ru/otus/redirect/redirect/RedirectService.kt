package ru.otus.redirect.redirect

import org.springframework.stereotype.Service
import ru.otus.redirect.rule.chain.RuleChainService
import util.lazyLogger

@Service
class RedirectService(
    private val ruleChainService: RuleChainService,
) {

    private val logger by lazyLogger()

    fun getLink(uri: String, args: Map<String, Any>): String {
        logger.info("Get Link. Started: $uri")
        val ruleChain = ruleChainService.getRuleChain(uri)
        return ruleChain.executeChain(args)
    }
}
