package ru.otus.redirect.rule.pack

data class RulePackModel(
    val id: Long? = null,
    val uri: String,
    val pack: List<Map<String, String>> = emptyList(),
)