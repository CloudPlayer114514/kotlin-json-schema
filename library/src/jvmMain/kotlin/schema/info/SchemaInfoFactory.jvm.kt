package com.yunbao.kotlin.schema.schema.info

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.json.JsonElement

@OptIn(ExperimentalSerializationApi::class)
class JvmSchemaInfoFactory : SchemaInfoFactory() {
    override fun buildExtraProperties(serialDescriptor: SerialDescriptor): Map<String, JsonElement> {
        // todo: merged annotation
        return super.buildExtraProperties(serialDescriptor)
    }
}

@OptIn(ExperimentalSerializationApi::class)
actual fun schemaInfoFactory(): SchemaInfoFactory {
    return JvmSchemaInfoFactory()
}