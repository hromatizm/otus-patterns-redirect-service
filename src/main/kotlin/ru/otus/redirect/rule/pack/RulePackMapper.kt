package ru.otus.redirect.rule.pack

fun RulePackEntity.toModel() =
    RulePackModel(
        id = id,
        uri = uri,
        pack = pack,
    )

fun RulePackModel.toEntity() =
    RulePackEntity(
        id = id,
        uri = uri,
        pack = pack
    )

fun RulePackDto.toModel() =
    RulePackModel(
        id = id,
        uri = uri,
        pack = pack
    )

fun RulePackModel.toDto() =
    RulePackDto(
        id = id,
        uri = uri,
        pack = pack
    )