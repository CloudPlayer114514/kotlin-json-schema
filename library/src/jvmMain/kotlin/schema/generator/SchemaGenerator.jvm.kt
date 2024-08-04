package com.yunbao.kotlin.schema.schema.generator

import com.yunbao.kotlin.schema.function.FunctionSerialDescriptor
import com.yunbao.kotlin.schema.schema.module.GlobalSchemaModule
import com.yunbao.kotlin.schema.schema.module.SchemaModule
import com.yunbao.kotlin.schema.schema.module.getOrRegisterParser
import com.yunbao.kotlin.schema.schema.parse.ObjectSchemaParser
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.serializer
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.typeOf

actual sealed class SchemaGenerator actual constructor(val schemaModule: SchemaModule) {
    actual companion object : SchemaGenerator(GlobalSchemaModule)

    actual fun createSchema(type: KType): JsonObject {
        val descriptor = serializer(type).descriptor
        return schemaModule.getOrRegisterParser(descriptor) {
            ObjectSchemaParser(it)
        }.parse(descriptor)
    }

    actual inline fun <reified T> createSchema(): JsonObject {
        return createSchema(typeOf<T>())
    }

    actual fun createSchema(function: KFunction<*>): JsonObject {
        val descriptor = FunctionSerialDescriptor(function)
        return schemaModule.getOrRegisterParser(descriptor) {
            ObjectSchemaParser(it)
        }.parse(descriptor)
    }
}