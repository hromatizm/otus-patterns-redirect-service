package ru.otus.redirect.rule.pack

fun RulePackEntity.toDto() =
    RulePackDto(
        id = id,
        url = url,
        pack = pack
    )

fun RulePackDto.toEntity() =
    RulePackEntity(
        id = id,
        url = url,
        pack = pack
    )