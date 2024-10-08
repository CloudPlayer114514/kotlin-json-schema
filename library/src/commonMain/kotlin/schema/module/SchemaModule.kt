package com.yunbao.kotlin.schema.schema.module

import com.yunbao.kotlin.schema.schema.parse.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor

internal val defaultParsers = mutableListOf(
    ArraySchemaParser(), ObjectSchemaParser(), EnumSchemaParser(),
    StringSchemaParser(), BooleanSchemaParser(), ByteSchemaParser(),
    ShortSchemaParser(), IntSchemaParser(), LongSchemaParser(),
    FloatSchemaParser(), DoubleSchemaParser(), CharSchemaParser(), EmptySchemaParser(),
)

interface SchemaModule {
    val parsers: Map<SerialDescriptor, SchemaParser>

    fun parserOrNull(descriptor: SerialDescriptor): SchemaParser?

    @OptIn(ExperimentalSerializationApi::class)
    fun parser(descriptor: SerialDescriptor): SchemaParser {
        return parserOrNull(descriptor) ?: defaultParsers.find { it.support(descriptor) }
        ?: error("No parser found for $descriptor")
    }
}

interface MutableSchemaModule : SchemaModule {
    fun registerParser(registerFor: SerialDescriptor, parser: SchemaParser)

    fun updateFrom(module: SchemaModule) {
        module.parsers.forEach { (descriptor, parser) ->
            registerParser(descriptor, parser)
        }
    }
}

fun MutableSchemaModule.getOrRegisterParser(
    descriptor: SerialDescriptor,
    parser: (SchemaModule) -> SchemaParser
): SchemaParser {
    return parserOrNull(descriptor) ?: parser(this).also { registerParser(descriptor, it) }
}