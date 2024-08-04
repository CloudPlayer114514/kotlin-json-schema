package com.yunbao.kotlin.schema.schema.parse

import com.yunbao.kotlin.schema.schema.info.SchemaInfo
import com.yunbao.kotlin.schema.schema.info.schemaInfoFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

interface SchemaParser {
    val schema: String

    val type: String

    @OptIn(ExperimentalSerializationApi::class)
    fun parseProperties(schemaInfo: SchemaInfo): Map<String, JsonElement>

    @OptIn(ExperimentalSerializationApi::class)
    fun parse(descriptor: SerialDescriptor, appendSchema: Boolean = true): JsonObject {
        val factory = schemaInfoFactory()
        val properties = parseProperties(factory.build(descriptor))
        return buildJsonObject {
            if (appendSchema) {
                put("\$schema", schema)
            }
            put("type", type)
            properties.forEach {
                put(it.key, it.value)
            }
        }
    }

    @ExperimentalSerializationApi
    fun support(descriptor: SerialDescriptor): Boolean
}