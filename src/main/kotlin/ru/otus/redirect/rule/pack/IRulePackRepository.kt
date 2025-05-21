package ru.otus.redirect.rule.pack

interface IRulePackRepository {

    fun existsByUri(uri: String): Boolean

    fun findByUri(uri: String): RulePackModel?

    fun findByUriOrElseThrow(uri: String): RulePackModel

    fun save(model: RulePackModel): RulePackModel

    fun deleteById(id: Long)
}