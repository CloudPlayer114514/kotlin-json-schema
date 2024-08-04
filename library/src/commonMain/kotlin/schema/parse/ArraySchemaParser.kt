package com.yunbao.kotlin.schema.schema.parse

import com.yunbao.kotlin.schema.schema.info.SchemaInfo
import com.yunbao.kotlin.schema.schema.module.GlobalSchemaModule
import com.yunbao.kotlin.schema.schema.module.SchemaModule
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.StructureKind
import kotlinx.serialization.json.JsonElement

class ArraySchemaParser(
    private val module: SchemaModule = GlobalSchemaModule,
    override val schema: String
    = "https://json-schema.org/draft/2020-12/schema",
) : SchemaParser {
    override val type: String
        get() = "array"

    @ExperimentalSerializationApi
    override fun parseProperties(schemaInfo: SchemaInfo): Map<String, JsonElement> {
        val descriptor = schemaInfo.elements[0].descriptor
        val jsonObject = module.parser(descriptor).parse(descriptor, false)
        return mapOf("item" to jsonObject)
    }

    @ExperimentalSerializationApi
    override fun support(descriptor: SerialDescriptor): Boolean {
        return descriptor.kind == StructureKind.LIST
    }
}