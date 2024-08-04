package com.yunbao.kotlin.schema.schema.parse

import com.yunbao.kotlin.schema.schema.info.SchemaInfo
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonArray

class EnumSchemaParser(
    override val schema: String
    = "https://json-schema.org/draft/2020-12/schema",
) : SchemaParser {
    override val type: String
        get() = "string"

    @OptIn(ExperimentalSerializationApi::class)
    override fun parseProperties(schemaInfo: SchemaInfo): Map<String, JsonElement> {
        val array = buildJsonArray {
            schemaInfo.elements.forEach {
                add(it.name)
            }
        }
        return mapOf("enum" to array)
    }

    @ExperimentalSerializationApi
    override fun support(descriptor: SerialDescriptor): Boolean {
        return descriptor.kind == SerialKind.ENUM
    }
}