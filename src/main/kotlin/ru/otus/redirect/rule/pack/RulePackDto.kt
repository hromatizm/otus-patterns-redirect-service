package ru.otus.redirect.rule.pack

data class RulePackDto(
    var id: Long? = null,
    var url: String,
    var pack: List<Map<String, String>> = emptyList(),
)