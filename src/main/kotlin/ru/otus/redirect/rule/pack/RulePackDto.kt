package ru.otus.redirect.rule.pack

data class RulePackDto(
    val id: Long? = null,
    val uri: String,
    val pack: List<Map<String, String>> = emptyList(),
)