package com.yunbao.kotlin.schema.ksp.resolver.cls

import com.google.devtools.ksp.getDeclaredProperties
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.yunbao.kotlin.schema.env.Environment.log
import com.yunbao.kotlin.schema.env.SCHEMA_FULL_NAME
import com.yunbao.kotlin.schema.ksp.resolver.SchemaResolver
import com.yunbao.kotlin.schema.metadata.ParameterMetadata
import com.yunbao.kotlin.schema.metadata.SchemaMetadata

class ClassSchemaResolver(override val resolver: Resolver) : SchemaResolver(resolver) {
    override fun getSchemaMetadata(): Sequence<SchemaMetadata> {
        return resolver.getSymbolsWithAnnotation(SCHEMA_FULL_NAME)
            .filterIsInstance<KSClassDeclaration>()
            .map {
                val constructor = it.primaryConstructor
                    ?: return@map SchemaMetadata(it.simpleName.asString(), true, emptyList(), it.annotations)
                val parameters = constructor.parameters
                val properties = it.getDeclaredProperties().mapTo(HashSet()) { prop ->
                    prop.simpleName
                }
                parameters.forEach { p ->
                    if (p.name !in properties) {
                        log.warn("Property ${p.name} not found in class ${it.simpleName.asString()} primary constructor. its properties will be ignored.")
                        return@map SchemaMetadata(it.simpleName.asString(), true, emptyList(), it.annotations)
                    }
                }
                val parameterMetadata = parameters.map(::ParameterMetadata)
                SchemaMetadata(
                    name = it.simpleName.asString(),
                    isClass = true,
                    parameters = parameterMetadata,
                    annotations = it.annotations
                )
            }
    }
}