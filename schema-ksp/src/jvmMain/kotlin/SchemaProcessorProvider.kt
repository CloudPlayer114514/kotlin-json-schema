package com.yunbao.kotlin.schema

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.yunbao.kotlin.schema.env.Environment
import com.yunbao.kotlin.schema.ksp.SchemaProcessor

class SchemaProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        Environment(environment)
        return SchemaProcessor()
    }
}