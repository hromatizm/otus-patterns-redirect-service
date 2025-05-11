package ru.otus.redirect.rule.pack

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface IRulePackJpaRepository : JpaRepository<RulePackEntity, Long> {

    fun existsByUrl(url: String): Boolean

    fun findByUrl(url: String): RulePackEntity?
}

@Repository
class RulePackRepository(
    private val jpaDelegate: IRulePackJpaRepository
) : IRulePackRepository {


    override fun existsByUrl(url: String): Boolean {
        return jpaDelegate.existsByUrl(url)
    }

    override fun findByUrl(url: String): RulePackModel? {
        val entity = jpaDelegate.findByUrl(url)
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