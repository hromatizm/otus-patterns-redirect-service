package ru.otus.redirect.rule.chain

import ru.otus.checker.Checker

data class RuleChainModel(
    val checkers: List<Checker>,
    val redirectUrl: String,
    val next: RuleChainModel?,
    val defaultUrl: String?,
) {

    fun executeChain(args: Map<String, Any>): String? {
        return when {
            checkers.all { it.check(args) } -> redirectUrl
            next != null -> next.executeChain(args)
            else -> defaultUrl
        }
    }

}