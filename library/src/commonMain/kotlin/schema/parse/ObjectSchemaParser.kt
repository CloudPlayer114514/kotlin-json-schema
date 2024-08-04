package com.yunbao.kotlin.schema.schema.parse

import com.yunbao.kotlin.schema.schema.info.SchemaInfo
import com.yunbao.kotlin.schema.schema.module.GlobalSchemaModule
import com.yunbao.kotlin.schema.schema.module.SchemaModule
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject

class ObjectSchemaParser(
    private val module: SchemaModule = GlobalSchemaModule,
    override val schema: String
    = "https://json-schema.org/draft/2020-12/schema",
) : SchemaParser {
    override val type: String
        get() = "object"

    @OptIn(ExperimentalSerializationApi::class)
    override fun parseProperties(schemaInfo: SchemaInfo): Map<String, JsonElement> {
        val elements = schemaInfo.elements
        val jsonObject = buildJsonObject {
            if (elements.isNotEmpty()) {
                elements.forEach {
                    val elementDescriptor = it.descriptor
                    val parser = module.parser(elementDescriptor)
                    val jsonObject = parser.parse(elementDescriptor, false)
                    put(it.name, jsonObject)
                }
            }
        }
        return schemaInfo.extraProperties + mapOf("properties" to jsonObject)
    }

    @ExperimentalSerializationApi
    override fun support(descriptor: SerialDescriptor): Boolean {
        val kind: SerialKind = descriptor.kind
        return kind == StructureKind.CLASS ||
                kind == StructureKind.OBJECT ||
                kind == SerialKind.CONTEXTUAL ||
                kind is PolymorphicKind
    }
}