package com.yunbao.kotlin.schema.schema.info

import com.yunbao.kotlin.schema.annotations.Schema
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.json.JsonElement
import kotlin.reflect.ExperimentalAssociatedObjects
import kotlin.reflect.findAssociatedObject

@OptIn(ExperimentalSerializationApi::class)
class NativeSchemaInfoFactory : SchemaInfoFactory() {
    @OptIn(ExperimentalAssociatedObjects::class)
    override fun buildExtraProperties(serialDescriptor: SerialDescriptor): Map<String, JsonElement> {
        // TODO("Not yet implemented")
        val schemas = serialDescriptor.annotations.filterIsInstance<Schema>()
        val clazz = schemas[0].clazz

        clazz.findAssociatedObject<Schema>()
        return super.buildExtraProperties(serialDescriptor)
    }
}

@OptIn(ExperimentalSerializationApi::class)
actual fun schemaInfoFactory(): SchemaInfoFactory {
    return NativeSchemaInfoFactory()
}