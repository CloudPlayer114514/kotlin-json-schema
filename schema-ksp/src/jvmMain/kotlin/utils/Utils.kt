package com.yunbao.kotlin.schema.utils

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import kotlin.reflect.KClass

fun KSClassDeclaration.companionObject(): KSClassDeclaration? {
    return declarations.find {
        it is KSClassDeclaration && it.isCompanionObject
    } as KSClassDeclaration?
}

infix fun KSAnnotation.isAnnotationTypeOf(annotationType: KClass<out Annotation>): Boolean {
    return isAnnotationTypeOf(annotationType.qualifiedName ?: return false)
}

infix fun KSAnnotation.isAnnotationTypeOf(annotationType: String): Boolean {
    val simpleName = annotationType.substringAfterLast(".")
    return shortName.getShortName() == simpleName && this.annotationType.resolve().declaration
        .qualifiedName?.asString() == annotationType
}

val KSAnnotation.annotations: Sequence<KSAnnotation>
    get() = annotationType.resolve().declaration.annotations