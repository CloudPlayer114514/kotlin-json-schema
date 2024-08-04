package com.yunbao.kotlin.schema.schema.info

import com.yunbao.kotlin.schema.annotations.ExtraProperty
import com.yunbao.kotlin.schema.util.toJsonElements
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.json.JsonElement

@ExperimentalSerializationApi
open class SchemaInfoFactory @PublishedApi internal constructor() {
    protected open fun buildKind(serialDescriptor: SerialDescriptor): SerialKind {
        return serialDescriptor.kind
    }

    protected open fun buildAnnotations(serialDescriptor: SerialDescriptor): List<Annotation> {
        return serialDescriptor.annotations
    }

    protected open fun buildDescriptor(serialDescriptor: SerialDescriptor): SerialDescriptor {
        return serialDescriptor
    }

    protected open fun buildElements(serialDescriptor: SerialDescriptor): List<SchemaInfo> {
        val elementsCount = serialDescriptor.elementsCount
        if (elementsCount == 0) return emptyList()
        return buildList {
            for (i in 0..<elementsCount) {
                val name = serialDescriptor.getElementName(i)
                val elementDescriptor = serialDescriptor.getElementDescriptor(i)
                val kind = elementDescriptor.kind
                val annotations = serialDescriptor.getElementAnnotations(i)
                val extraProperties = annotations.filterIsInstance<ExtraProperty>().toJsonElements()
                val info = SchemaInfo(name, kind, annotations, buildElements(elementDescriptor), elementDescriptor, extraProperties)
                this += info
            }
        }
    }

    protected open fun buildExtraProperties(serialDescriptor: SerialDescriptor): Map<String, JsonElement> {
        return serialDescriptor.annotations.filterIsInstance<ExtraProperty>().toJsonElements()
    }

    fun build(serialDescriptor: SerialDescriptor): SchemaInfo {
        return build(serialDescriptor, serialDescriptor.serialName)
    }

    protected open fun build(serialDescriptor: SerialDescriptor, name: String): SchemaInfo {
        return SchemaInfo(
            name = name,
            kind = buildKind(serialDescriptor),
            annotations = buildAnnotations(serialDescriptor),
            elements = buildElements(serialDescriptor),
            descriptor = buildDescriptor(serialDescriptor),
            extraProperties = buildExtraProperties(serialDescriptor),
        )
    }
}

@OptIn(ExperimentalSerializationApi::class)
expect fun schemaInfoFactory(): SchemaInfoFactory