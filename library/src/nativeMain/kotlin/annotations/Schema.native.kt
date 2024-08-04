package com.yunbao.kotlin.schema.annotations

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialInfo
import kotlin.reflect.KClass

/**
 * 用于指示 ksp 为注解所在的类生成对应的 schema 扩展属性。
 * schema 属性是 ksp 为该类或函数生成 JSON Schema（版本 2020_12，根据主构造器中的属性，或是函数的入参生成）。
 *
 */
@OptIn(ExperimentalSerializationApi::class)
@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@SerialInfo
actual annotation class Schema(val clazz: KClass<*> = Any::class)