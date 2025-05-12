package ru.otus.redirect.exception

class RulePackAlreadyExistsException(uri: String) :
    RuntimeException("Rule pack for uri: $uri already exists")

class RulePackNotExistsException(uri: String) :
    RuntimeException("Rule pack for uri: $uri not exists")

class EmptyRulePackException(uri: String) :
    RuntimeException("Rule pack for uri: $uri is empty")

class NoArgPrimaryConstructorException(className: String?) :
    RuntimeException("Class $className must have a primary constructor without parameters")