package ru.otus.redirect.exception

class RulePackAlreadyExistsException(url: String) :
    RuntimeException("Rule pack for url: $url already exists")

class RulePackNotExistsException(url: String) :
    RuntimeException("Rule pack for url: $url not exists")