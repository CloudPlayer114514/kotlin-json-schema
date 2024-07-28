package com.yunbao.kotlin.schema.ksp.resolver.functions

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.yunbao.kotlin.schema.env.SCHEMA_FULL_NAME
import com.yunbao.kotlin.schema.ksp.resolver.SchemaResolver
import com.yunbao.kotlin.schema.metadata.ParameterMetadata
import com.yunbao.kotlin.schema.metadata.SchemaMetadata

class FunctionSchemaResolver(override val resolver: Resolver) : SchemaResolver(resolver) {
    override fun getSchemaMetadata(): Sequence<SchemaMetadata> {
        return resolver.getSymbolsWithAnnotation(SCHEMA_FULL_NAME)
            .filterIsInstance<KSFunctionDeclaration>()
            .map {
                val parameterMetadata = it.parameters.map { p ->
                    ParameterMetadata(p)
                }
                SchemaMetadata(
                    name = it.simpleName.asString(),
                    isClass = false,
                    parameters = parameterMetadata,
                    annotations = it.annotations
                )
            }
    }
}