package ru.otus.redirect.rule.pack

interface IRulePackRepository {

    fun existsByUrl(url: String): Boolean

    fun findByUrl(url: String): RulePackModel?

    fun save(model: RulePackModel): RulePackModel

    fun deleteById(id: Long)
}