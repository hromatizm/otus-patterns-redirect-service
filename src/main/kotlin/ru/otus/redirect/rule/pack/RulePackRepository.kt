package ru.otus.redirect.rule.pack

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.otus.redirect.exception.RulePackNotExistsException

interface IRulePackJpaRepository : JpaRepository<RulePackEntity, Long> {

    fun existsByUri(uri: String): Boolean

    fun findByUri(uri: String): RulePackEntity?
}

@Repository
class RulePackRepository(
    private val jpaDelegate: IRulePackJpaRepository
) : IRulePackRepository {


    override fun existsByUri(uri: String): Boolean {
        return jpaDelegate.existsByUri(uri)
    }

    override fun findByUri(uri: String): RulePackModel? {
        val entity = jpaDelegate.findByUri(uri)
        return entity?.toModel()
    }

   override fun findByUriOrElseThrow(uri: String): RulePackModel = findByUri(uri) ?: throw RulePackNotExistsException(uri)

    override fun save(model: RulePackModel): RulePackModel {
        val entity = jpaDelegate.save(model.toEntity())
        return entity.toModel()
    }

    override fun deleteById(id: Long) {
        jpaDelegate.deleteById(id)
    }
}