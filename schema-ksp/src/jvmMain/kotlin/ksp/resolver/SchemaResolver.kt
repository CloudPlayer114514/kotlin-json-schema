package com.yunbao.kotlin.schema.ksp.resolver

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.yunbao.kotlin.schema.metadata.SchemaMetadata

abstract class SchemaResolver(protected open val resolver: Resolver) {
    companion object {
        private val resolvers = mutableSetOf<SchemaResolver>()

        val deferredSymbols: List<KSAnnotated>
            get() = resolvers.flatMap { it.deferredSymbols }

        fun registerResolver(resolver: SchemaResolver) {
            resolvers += resolver
        }

        fun resolve(): Sequence<SchemaMetadata> {
            return resolvers.fold(emptySequence()) { acc, schemaResolver ->
                acc + schemaResolver.getSchemaMetadata()
            }
        }
    }

    private val _deferredSymbols = mutableListOf<KSAnnotated>()

    val deferredSymbols: List<KSAnnotated>
        get() = _deferredSymbols

    abstract fun getSchemaMetadata(): Sequence<SchemaMetadata>

    fun create() {
        val schemaMetadata = getSchemaMetadata()
        schemaMetadata.forEach { schema ->
            schema.annotations.forEach {

            }
        }
    }
}