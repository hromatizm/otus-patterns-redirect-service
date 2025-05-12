package ru.otus.redirect.rule.pack

interface IRulePackRepository {

    fun existsByUri(uri: String): Boolean

    fun findByUrl(uri: String): RulePackModel?

    fun save(model: RulePackModel): RulePackModel

    fun deleteById(id: Long)
}