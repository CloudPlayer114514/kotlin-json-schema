package com.yunbao.kotlin.schema.schema.generator

import com.yunbao.kotlin.schema.schema.module.SchemaModule
import kotlinx.serialization.json.JsonObject
import kotlin.reflect.KFunction
import kotlin.reflect.KType

expect sealed class SchemaGenerator(schemaModule: SchemaModule) {
    val schemaModule: SchemaModule

    companion object : SchemaGenerator

    fun createSchema(type: KType): JsonObject

    fun createSchema(function: KFunction<*>): JsonObject

    inline fun <reified T> createSchema(): JsonObject
}