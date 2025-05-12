package ru.otus.redirect.rule.pack

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

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

    override fun findByUrl(uri: String): RulePackModel? {
        val entity = jpaDelegate.findByUri(uri)
        return entity?.toModel()
    }

    override fun save(model: RulePackModel): RulePackModel {
        val entity = jpaDelegate.save(model.toEntity())
        return entity.toModel()
    }

    override fun deleteById(id: Long) {
        jpaDelegate.deleteById(id)
    }
}