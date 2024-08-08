package com.yunbao.kotlin.schema.metadata

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.yunbao.kotlin.schema.annotations.MergeFor
import com.yunbao.kotlin.schema.utils.isAnnotationTypeOf

data class MergedMetadata(
    val propertyName: String,
    val targetAnnotation: KSClassDeclaration,
) {
    companion object {
        fun ofOrNull(valueArgument: KSPropertyDeclaration): MergedMetadata? {
            val ksAnnotation = valueArgument.annotations.singleOrNull {
                it isAnnotationTypeOf MergeFor::class
            } ?: return null
            val mergeFor = ksAnnotation.arguments.find {
                it.name?.asString() == "propertyName"
            }?.value as String
            val targetAnnotation = ksAnnotation.arguments.find {
                it.name?.asString() == "targetAnnotation"
            }?.value.let {
                it as KSType
                it.declaration as KSClassDeclaration
            }
            val propertyName = mergeFor.ifBlank {
                valueArgument.simpleName.asString()
            }
            return MergedMetadata(propertyName, targetAnnotation)
        }

        fun createByAnnotation(ksAnnotation: KSAnnotation): List<MergedMetadata> {
            if (ksAnnotation.arguments.isEmpty()) return emptyList()
            val declaration = ksAnnotation.annotationType.resolve().declaration as KSClassDeclaration
            return buildList {
                for (valueArgument in declaration.getAllProperties()) {
                    val mergedMetadata = ofOrNull(valueArgument)
                    if (mergedMetadata != null) {
                        add(mergedMetadata)
                    }
                }
            }
        }
    }
}