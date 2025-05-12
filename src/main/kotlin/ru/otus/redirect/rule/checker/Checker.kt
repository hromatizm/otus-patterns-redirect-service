package ru.otus.redirect.rule.checker

import ru.otus.redirect.exception.NoArgPrimaryConstructorException
import kotlin.reflect.full.primaryConstructor

abstract class Checker {

    abstract val code: String

    var next: Checker? = null

    abstract fun check(args: Map<String, Any>): Boolean

    fun copy(): Checker {
        val constructor = this::class.primaryConstructor
            ?.takeIf { it.parameters.isEmpty() }
            ?: throw NoArgPrimaryConstructorException(className = this::class.simpleName)
        return constructor.call()
    }

}
