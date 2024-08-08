package com.yunbao.kotlin.schema.ksp.annotations

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSType
import com.yunbao.kotlin.schema.utils.annotations
import com.yunbao.kotlin.schema.utils.isAnnotationTypeOf
import kotlin.reflect.KClass

@JvmInline
value class AnnotationScanner(
    private val annotations: Sequence<KSAnnotation>,
) {
    constructor(annotatedElement: KSAnnotated) : this(annotatedElement.annotations)

    fun isPresent(annotationType: String): Boolean {
        return isDirectlyPresent(annotationType) || isMetaPresent(annotationType)
    }

    fun isPresent(annotationType: KClass<out Annotation>): Boolean {
        return isPresent(annotationType.qualifiedName ?: return false)
    }

    fun isDirectlyPresent(annotationType: String): Boolean {
        return annotations.find { it isAnnotationTypeOf annotationType } != null
    }

    fun isDirectlyPresent(annotationType: KClass<out Annotation>): Boolean {
        return isDirectlyPresent(annotationType.qualifiedName ?: return false)
    }

    fun isMetaPresent(annotationType: String): Boolean {
        var found = false
        doWithAnnotation { anno, _ ->
            if (found) return@doWithAnnotation
            if (anno isAnnotationTypeOf annotationType) {
                found = true
            }
        }
        return found
    }

    fun isMetaPresent(annotationType: KClass<out Annotation>): Boolean {
        return isMetaPresent(annotationType.qualifiedName ?: return false)
    }

    fun doWithAnnotation(
        forEach: (KSAnnotation, level: Int) -> Unit,
    ) {
        val scannedAnnotations = mutableSetOf<KSType>()
        fun doIsMetaPresent(annotations: Sequence<KSAnnotation>, level: Int = 0) {
            annotations.forEach {
                val ksType = it.annotationType.resolve()
                if (!scannedAnnotations.add(ksType)) {
                    return@forEach
                }
                forEach(it, level)
                doIsMetaPresent(ksType.declaration.annotations, level + 1)
            }
        }
        doIsMetaPresent(annotations)
    }

    fun findAnnotation(annotationType: String): List<KSAnnotation> = buildList {
        doWithAnnotation { anno, _ ->
            if (anno isAnnotationTypeOf annotationType) {
                add(anno)
            }
        }
    }

    fun findAnnotation(annotationType: KClass<out Annotation>): List<KSAnnotation> {
        return findAnnotation(annotationType.qualifiedName ?: return emptyList())
    }

    fun findAnnotated(annotationType: String): List<KSAnnotation> = buildList {
        annotations.forEach {
            val present = AnnotationScanner(it.annotations).isPresent(annotationType)
            if (present) {
                add(it)
            }
        }
    }

    fun findAnnotated(annotationType: KClass<out Annotation>): List<KSAnnotation> {
        return findAnnotated(annotationType.qualifiedName ?: return emptyList())
    }
}