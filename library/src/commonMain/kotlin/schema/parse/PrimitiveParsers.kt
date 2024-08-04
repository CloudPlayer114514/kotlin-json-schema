package com.yunbao.kotlin.schema.schema.parse

import com.yunbao.kotlin.schema.schema.info.SchemaInfo
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive

sealed class PrimitiveParser(
    override val type: String,
    private val format: String?,
    private val primitiveKind: PrimitiveKind,
    override val schema: String
    = "https://json-schema.org/draft/2020-12/schema",
) : SchemaParser {
    @OptIn(ExperimentalSerializationApi::class)
    override fun parseProperties(schemaInfo: SchemaInfo): Map<String, JsonElement> {
        return format?.let {
            mapOf("format" to JsonPrimitive(it))
        } ?: emptyMap()
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun support(descriptor: SerialDescriptor): Boolean {
        return descriptor.kind == primitiveKind
    }

    override fun toString(): String {
        return "${this::class.simpleName}(type='$type', format=$format, primitiveKind=$primitiveKind, schema='$schema')"
    }
}

class ByteSchemaParser(schema: String = "https://json-schema.org/draft/2020-12/schema") :
    PrimitiveParser("integer", null, PrimitiveKind.BYTE, schema)

class ShortSchemaParser(schema: String = "https://json-schema.org/draft/2020-12/schema") :
    PrimitiveParser("integer", null, PrimitiveKind.SHORT, schema)

class IntSchemaParser(schema: String = "https://json-schema.org/draft/2020-12/schema") :
    PrimitiveParser("integer", "int32", PrimitiveKind.INT, schema)

class LongSchemaParser(schema: String = "https://json-schema.org/draft/2020-12/schema") :
    PrimitiveParser("integer", "int64", PrimitiveKind.LONG, schema)

class FloatSchemaParser(schema: String = "https://json-schema.org/draft/2020-12/schema") :
    PrimitiveParser("number", "float", PrimitiveKind.FLOAT, schema)

class DoubleSchemaParser(schema: String = "https://json-schema.org/draft/2020-12/schema") :
    PrimitiveParser("number", "double", PrimitiveKind.DOUBLE, schema)

class CharSchemaParser(schema: String = "https://json-schema.org/draft/2020-12/schema") :
    PrimitiveParser("string", null, PrimitiveKind.CHAR, schema)

class StringSchemaParser(schema: String = "https://json-schema.org/draft/2020-12/schema") :
    PrimitiveParser("string", null, PrimitiveKind.STRING, schema)

class BooleanSchemaParser(schema: String = "https://json-schema.org/draft/2020-12/schema") :
    PrimitiveParser("boolean", null, PrimitiveKind.BOOLEAN, schema)
