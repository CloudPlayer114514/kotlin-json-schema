package com.yunbao.kotlin.schema.ksp

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.yunbao.kotlin.schema.annotations.ExtraProperty
import com.yunbao.kotlin.schema.annotations.MetaAnnotation
import com.yunbao.kotlin.schema.env.Environment
import com.yunbao.kotlin.schema.ksp.annotations.AnnotationScanner
import com.yunbao.kotlin.schema.ksp.resolver.SchemaResolver
import com.yunbao.kotlin.schema.ksp.resolver.cls.ClassSchemaResolver
import com.yunbao.kotlin.schema.ksp.resolver.functions.FunctionSchemaResolver

class SchemaProcessor : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        SchemaResolver.registerResolver(FunctionSchemaResolver(resolver))
        SchemaResolver.registerResolver(ClassSchemaResolver(resolver))
        SchemaResolver.resolve().forEach {
            val scanner = AnnotationScanner(it.annotations)

            val present = scanner.findAnnotated(MetaAnnotation::class)
            val extraProperties = scanner.findAnnotation(ExtraProperty::class)
            Environment.log.warn("SchemaProcessor: ${it.name} annotated: $present, ext: $extraProperties")
        }
        return SchemaResolver.deferredSymbols
    }
}