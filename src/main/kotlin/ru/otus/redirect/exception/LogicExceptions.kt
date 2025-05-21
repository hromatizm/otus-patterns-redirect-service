package ru.otus.redirect.exception

class RulePackAlreadyExistsException(uri: String) :
    RuntimeException("Rule pack for uri: $uri already exists")

class RulePackNotExistsException(uri: String) :
    RuntimeException("Rule pack for uri: $uri not exists")

class EmptyRulePackException(uri: String) :
    RuntimeException("Rule pack for uri: $uri is empty")

class ConditionNotFoundException(code: String) :
    RuntimeException("Condition with code: $code not found")

class NoConditionImplementationException() : RuntimeException("No Condition implementation found in JAR")
