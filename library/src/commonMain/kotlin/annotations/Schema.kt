package annotations

import json.JsonType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialInfo
import kotlin.reflect.KClass

/**
 * 用于添加一对额外的 Json 的 key-value 键值对的注解。
 * 可以作用于 class 或者 property 上，也可以作用于注解上。作用于注解上时，注解本身也拥有了增添额外键值对的能力。
 * @param key json 的 key 。
 * @param value json 的 value 。
 * @param jsonType json 的 value 的类型，默认为 JsonElement 。如果不为 JsonElement ，则尝试将 value 解析成指定的类型。
 */
@OptIn(ExperimentalSerializationApi::class)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
@SerialInfo
annotation class Extra(
    val key: String = "",
    val value: String = "",
    val jsonType: JsonType = JsonType.JsonElement
)

/**
 * 指示注解中的该属性为其注解上对应 `annotationClass` 中名为 `attribute` 的属性。
 */
@SerialInfo
@OptIn(ExperimentalSerializationApi::class)
@Target(AnnotationTarget.PROPERTY)
@Repeatable
annotation class MetaPropertyFor(
    val attribute: String,
    val annotationClass: KClass<out Annotation>,
)

/**
 * 用于指示 ksp 为注解所在的类生成对应的 schema 扩展属性。
 * schema 属性是 ksp 为该类或函数生成 JSON Schema（版本 2020_12，根据主构造器中的属性，或是函数的入参生成）。
 *
 * ```
 * @CreateSchema
 * fun doSomeThing(name: String, age: Int, student: Student) = Unit
 *
 * // 入参需有 Serializable 注解才能够正常解析类型
 * @Serializable
 * @CreateSchema
 * data class Student(val studentId: Int, val address: List<String>)
 *
 * ::doSomeThing.schema // 将输出以下结果
 *
 * {
 *   "$schema" : "https://json-schema.org/draft/2020-12/schema",
 *   "type" : "object",
 *   "properties" : {
 *     "age" : {
 *       "type" : "integer",
 *       "format" : "int32"
 *     },
 *     "name" : {
 *       "type" : "string"
 *     },
 *     "student" : {
 *       "type" : "object",
 *       "properties" : {
 *         "address" : {
 *           "type" : "array",
 *           "items" : {
 *             "type" : "string"
 *           }
 *         },
 *         "studentId" : {
 *           "type" : "integer",
 *           "format" : "int32"
 *         }
 *       }
 *     }
 *   }
 * }
 *
 * ```
 */
@OptIn(ExperimentalSerializationApi::class)
@Target(AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@SerialInfo
annotation class CreateSchema

@Extra(key = "description", jsonType = JsonType.String)
@Target(AnnotationTarget.PROPERTY)
annotation class Description(
    @MetaPropertyFor("value", Extra::class)
    val description: String = ""
)

fun main() {

}