package com.yunbao.kotlin.schema.ksp.annotations

import kotlin.reflect.KClass

sealed class AnnotationElement<T : Any>(open val elementType: KClass<T>, open val element: T)

sealed class PrimitiveElement<T : Any>(override val elementType: KClass<T>, override val element: T) : AnnotationElement<T>(elementType, element)

data class NumberElement(override val element: Number) : PrimitiveElement<Number>(Number::class, element)

data class StringElement(override val element: String) : PrimitiveElement<String>(String::class, element)

data class BooleanElement(override val element: Boolean) : PrimitiveElement<Boolean>(Boolean::class, element)

