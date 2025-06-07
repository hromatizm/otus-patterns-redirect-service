package ru.otus.redirect.rule.chain

import ru.otus.core.ICommand

data class RuleChainCmd(
    val conditions: List<ICommand<Boolean>>,
    val redirectUrl: String,
    val next: RuleChainCmd?,
    val defaultUrl: String?,
) : ICommand<String?> {

    override fun execute(args: Map<String, Any>): String? {
        return when {
            conditions.all { it.execute(args) } -> redirectUrl
            next != null -> next.execute(args)
            else -> defaultUrl
        }
    }

}