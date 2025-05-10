package ru.otus.redirect.rule.pack

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IRulePackRepository : JpaRepository<RulePackEntity, Long> {

    fun existsByUrl(url: String): Boolean

    fun findByUrl(url: String): RulePackEntity?
}