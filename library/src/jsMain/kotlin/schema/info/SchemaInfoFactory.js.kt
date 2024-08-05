package com.yunbao.kotlin.schema.schema.info

import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
actual fun schemaInfoFactory(): SchemaInfoFactory {
    return SchemaInfoFactory()
}