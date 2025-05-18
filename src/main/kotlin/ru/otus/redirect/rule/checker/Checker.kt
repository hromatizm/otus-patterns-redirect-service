package ru.otus.redirect.rule.checker

import ru.otus.redirect.exception.NoArgPrimaryConstructorException
import kotlin.reflect.full.primaryConstructor

abstract class Checker {

    abstract val code: String
    abstract val expectedValue: Any?

    var next: Checker? = null

    abstract fun check(args: Map<String, Any>): Boolean

    fun getInstance(expectedValue: Any): Checker {
        val constructor = this::class.primaryConstructor
            ?: throw NoArgPrimaryConstructorException(className = this::class.simpleName)
        return constructor.call(expectedValue)
    }

}
