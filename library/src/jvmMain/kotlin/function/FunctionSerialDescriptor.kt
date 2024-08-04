package com.yunbao.kotlin.schema.function

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.serialDescriptor
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter

interface FunctionSerialDescriptor<out F : KFunction<R>, out R> : SerialDescriptor {
    val function: F

    @ExperimentalSerializationApi
    override val elementsCount: Int
        get() = function.parameters.size

    @OptIn(ExperimentalSerializationApi::class)
    override val kind: SerialKind
        get() = SerialKind.CONTEXTUAL

    @ExperimentalSerializationApi
    override val serialName: String
        get() = function.name

    @ExperimentalSerializationApi
    override fun getElementDescriptor(index: Int): SerialDescriptor {
        return serialDescriptor(getParameter(index).type)
    }

    @ExperimentalSerializationApi
    override fun getElementName(index: Int): String {
        return getParameter(index).name ?: index.toString()
    }

    @ExperimentalSerializationApi
    override fun getElementIndex(name: String): Int {
        return function.parameters.indexOfFirst { it.name == name }
    }

    @ExperimentalSerializationApi
    override fun getElementAnnotations(index: Int): List<Annotation> {
        return getParameter(index).annotations
    }

    @ExperimentalSerializationApi
    override fun isElementOptional(index: Int): Boolean {
        return getParameter(index).isOptional
    }

    @ExperimentalSerializationApi
    override val annotations: List<Annotation>
        get() = function.annotations

    override val isInline: Boolean
        get() = function.isInline

    @ExperimentalSerializationApi
    override val isNullable: Boolean
        get() = false

    private fun getParameter(index: Int): KParameter {
        return function.parameters[index]
    }
}

fun <F : KFunction<R>, R> FunctionSerialDescriptor(function: F): FunctionSerialDescriptor<F, R> {
    return FunctionSerialDescriptorImpl(function)
}