package ru.otus.redirect.rule.pack

fun RulePackEntity.toModel() =
    RulePackModel(
        id = id,
        url = url,
        pack = pack
    )

fun RulePackModel.toEntity() =
    RulePackEntity(
        id = id,
        url = url,
        pack = pack
    )

fun RulePackDto.toModel() =
    RulePackModel(
        id = id,
        url = url,
        pack = pack
    )

fun RulePackModel.toDto() =
    RulePackDto(
        id = id,
        url = url,
        pack = pack
    )