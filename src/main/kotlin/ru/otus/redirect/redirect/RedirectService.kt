package ru.otus.redirect.redirect

import org.springframework.stereotype.Service
import ru.otus.redirect.rule.pack.RulePackService
import util.lazyLogger

@Service
class RedirectService(
    private val checkerService: RulePackService,
) {

    private val logger by lazyLogger()

    fun getLink(uri: String): String {
        logger.info("Get Link. Started: $uri")
        // val rulePack = rulePackService.get(uri)
        return ""
    }
}
