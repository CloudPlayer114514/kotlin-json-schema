package com.yunbao.kotlin.schema.schema.module

import com.yunbao.kotlin.schema.schema.parse.SchemaParser
import kotlinx.serialization.descriptors.SerialDescriptor

object GlobalSchemaModule : SchemaModule {
    private val runtimeParsers = mutableMapOf<SerialDescriptor, SchemaParser>()

    override val parsers: Map<SerialDescriptor, SchemaParser>
        get() = runtimeParsers

    override fun registerParser(registerFor: SerialDescriptor, parser: SchemaParser) {
        runtimeParsers[registerFor] = parser
    }

    override fun parserOrNull(descriptor: SerialDescriptor): SchemaParser? {
        return runtimeParsers[descriptor]
    }
}