package com.yunbao.kotlin.schema.metadata

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeReference
import com.google.devtools.ksp.symbol.KSValueParameter
import com.yunbao.kotlin.schema.env.Environment.log

data class ParameterMetadata(
    val name: String,
    val typeReference: KSTypeReference,
    val annotations: Sequence<KSAnnotation> = emptySequence(),
) {
    constructor(valueParameter: KSValueParameter) : this(
        valueParameter.name?.asString() ?: "null",
        valueParameter.type,
        valueParameter.annotations,
    )

    override fun toString(): String {
        return "ParameterMetadata(name='$name', typeReference=$typeReference, annotations=${annotations.toList()})"
    }
}

data class SchemaMetadata(
    val name: String,
    val isClass: Boolean,
    val parameters: List<ParameterMetadata>,
    val annotations: Sequence<KSAnnotation> = emptySequence(),
) {
    override fun toString(): String {
        val map = annotations.map {
            it.arguments.map { arg ->
                val value = arg.value
                if (value is KSType) {
                    log.warn("${arg.name?.asString()}'s declaration is ${value.declaration}, declaration type: ${value.declaration::class}")
                }
                Triple(arg.name?.asString(), arg.value, arg.value?.javaClass)
            }
        }
        return "SchemaMetadata(name='$name', isClass=$isClass, parameters=$parameters, annotations=${map.toList()})"
    }
}