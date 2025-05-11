package ru.otus.redirect.rule.checker

import ru.otus.redirect.exception.NoArgPrimaryConstructorException
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.primaryConstructor

abstract class Checker {

    abstract val code: String

    var next: Checker? = null

    abstract fun check(args: Map<String, Any>): Boolean

    fun executeChain(args: Map<String, Any>): Boolean {
        return when {
            check(args) -> true
            next != null -> next!!.executeChain(args)
            else -> false
        }
    }

    fun copy(): Checker {
        val constructor = this::class.getNoArgPrimaryConstructor()
        return constructor.call()
    }

    private fun KClass<out Checker>.getNoArgPrimaryConstructor(): KFunction<Checker> {
        return primaryConstructor
            ?.takeIf { it.parameters.isEmpty() }
            ?: throw NoArgPrimaryConstructorException(className = simpleName)
    }
}
