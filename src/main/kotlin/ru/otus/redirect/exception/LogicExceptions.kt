package ru.otus.redirect.exception

class RulePackAlreadyExistsException(url: String) :
    RuntimeException("Rule pack for url: $url already exists")

class RulePackNotExistsException(url: String) :
    RuntimeException("Rule pack for url: $url not exists")

class CheckerNotExistsException(code: String) :
    RuntimeException("Checker with code: $code not exists")

class NoArgPrimaryConstructorException(className: String?) :
    RuntimeException("Class $className must have a primary constructor without parameters")