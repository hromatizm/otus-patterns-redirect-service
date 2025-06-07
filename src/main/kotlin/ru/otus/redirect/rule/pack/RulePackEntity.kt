package ru.otus.redirect.rule.pack

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Type

@Entity()
@Table(name = "rules")
data class RulePackEntity(
    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var uri: String = "",

    @Type(JsonBinaryType::class)
    @Column(name = "pack", columnDefinition = "jsonb", nullable = false)
    var pack: List<Map<String, String>> = emptyList(),
)