package com.yunbao.kotlin.schema.ksp.annotations

import com.google.devtools.ksp.symbol.KSAnnotation
import com.yunbao.kotlin.schema.annotations.ExtraProperty
import com.yunbao.kotlin.schema.metadata.MergedMetadata
import com.yunbao.kotlin.schema.utils.annotations

class MergedExtraProperty(
    private val metaAnnotation: KSAnnotation,
) {
    private val annotations = metaAnnotation.annotations

    private val scanner = AnnotationScanner(annotations)

    private fun getMergedFor() {
        val list = MergedMetadata.createByAnnotation(metaAnnotation)
    }

    fun synthesized(): List<ExtraProperty> {
        metaAnnotation.annotations
        return emptyList()
    }
}