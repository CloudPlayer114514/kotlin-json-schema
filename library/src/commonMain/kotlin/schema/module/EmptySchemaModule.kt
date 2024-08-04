package com.yunbao.kotlin.schema.schema.module

import com.yunbao.kotlin.schema.schema.parse.SchemaParser
import kotlinx.serialization.descriptors.SerialDescriptor

internal object EmptySchemaModule : SchemaModule {
    override val parsers: Map<SerialDescriptor, SchemaParser>
        get() = emptyMap()

    override fun parserOrNull(descriptor: SerialDescriptor): SchemaParser? {
        return null
    }

    override fun registerParser(registerFor: SerialDescriptor, parser: SchemaParser) {
        error("EmptySchemaModule does not support registering parsers")
    }
}

fun emptySchemaModule(): SchemaModule = EmptySchemaModule