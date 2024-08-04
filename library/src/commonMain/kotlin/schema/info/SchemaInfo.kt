package com.yunbao.kotlin.schema.schema.info

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.json.JsonElement

@ExperimentalSerializationApi
data class SchemaInfo internal constructor(
    val name: String,
    val kind: SerialKind,
    val annotations: List<Annotation>,
    val elements: List<SchemaInfo>,
    val descriptor: SerialDescriptor,
    val extraProperties: Map<String, JsonElement> = emptyMap(),
) {
    companion object
}

@OptIn(ExperimentalSerializationApi::class)
fun SchemaInfo.Companion.create(descriptor: SerialDescriptor): SchemaInfo {
    return schemaInfoFactory().build(descriptor)
}