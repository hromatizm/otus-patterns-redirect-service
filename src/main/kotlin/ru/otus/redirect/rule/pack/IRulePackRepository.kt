package ru.otus.redirect.rule.pack

import ru.otus.redirect.exception.RulePackNotExistsException

interface IRulePackRepository {

    fun existsByUri(uri: String): Boolean

    fun findByUrl(uri: String): RulePackModel?

    fun findByUrlOrElseThrow(uri: String): RulePackModel = findByUrl(uri) ?: throw RulePackNotExistsException(uri)

    fun save(model: RulePackModel): RulePackModel

    fun deleteById(id: Long)
}