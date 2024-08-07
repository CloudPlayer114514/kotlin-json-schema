package com.yunbao.kotlin.schema.schema.parse

import com.yunbao.kotlin.schema.schema.info.SchemaInfo
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

class EmptySchemaParser(
    override val schema: String
    = "https://json-schema.org/draft/2020-12/schema",
) : SchemaParser {
    override val type: String
        get() = "object"

    @ExperimentalSerializationApi
    override fun parseProperties(schemaInfo: SchemaInfo): Map<String, JsonElement> {
        return mapOf("properties" to JsonObject(emptyMap()))
    }

    @ExperimentalSerializationApi
    override fun support(descriptor: SerialDescriptor): Boolean {
        return true
    }
}