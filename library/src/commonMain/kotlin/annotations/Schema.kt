package com.yunbao.kotlin.schema.annotations

import com.yunbao.kotlin.schema.json.JsonType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialInfo

/**
 * 用于添加一对额外的 Json 的 key-value 键值对的注解。
 * 可以作用于 class 或者 property 上。
 * 可以重复注解。
 * @param key json 的 key 。
 * @param value json 的 value 。
 * @param jsonType json 的 value 的类型，默认为 JsonElement 。如果不为 JsonElement ，则尝试将 value 解析成指定的类型。
 */
@OptIn(ExperimentalSerializationApi::class)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
@Repeatable
@SerialInfo
annotation class ExtraProperty(
    val key: String = "",
    val value: String = "",
    val jsonType: JsonType = JsonType.JSON_ELEMENT
)

/**
 * 指示被注解的类是一个需要处理的 Schema 属性键值对。
 * @param name schema 属性的名称，默认为空，表示使用类名作为 schema 属性的名称。
 * @param valueName 指示这个键的值。如果是空字符串，将尝试使用注解中唯一的属性去当做值，如果有多个属性而不指定则报错。
 * @param objectProperties 注解中应该被解析为额外对象的字段。这一项和 valueName 不能同时存在，
 * 如果同时存在，则 valueName 会被忽略，也会被放入对象之中。
 */
@SerialInfo
@OptIn(ExperimentalSerializationApi::class)
@Target(AnnotationTarget.ANNOTATION_CLASS)
@Repeatable
annotation class MetaAnnotation(
    val name: String = "",
    val valueName: String = "",
    vararg val objectProperties: String = [],
)

/**
 * 用于指示 ksp 为注解所在的类生成对应的 schema 扩展属性。
 * schema 属性是 ksp 为该类或函数生成 JSON Schema（版本 2020_12，根据主构造器中的属性，或是函数的入参生成）。
 *
 */
@OptIn(ExperimentalSerializationApi::class)
@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@SerialInfo
expect annotation class Schema()

@OptIn(ExperimentalSerializationApi::class)
@MetaAnnotation("description", "description")
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
@SerialInfo
annotation class Description(
    val description: String = ""
)

fun main() {

}