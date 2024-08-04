package com.yunbao.kotlin.schema.json

import kotlinx.serialization.json.*

/**
 * 在将字符串解析为 Json 对象时提供的建议。解析时将尝试将字符串中的内容与提供的类型相匹配，如果匹配失败，则将其解析为 JsonElement 。
 *
 * @author CloudPlayer
 */
enum class JsonType {
    NULL {
        override fun toJsonElement(value: String): JsonNull {
            return JsonNull
        }
    },
    STRING {
        override fun toJsonElement(value: String): JsonPrimitive {
            return JsonPrimitive(value)
        }
    },
    NUMBER {
        override fun toJsonElement(value: String): JsonPrimitive {
            return JsonPrimitive(value.toDoubleOrNull() ?: value.toLong())
        }
    },
    BOOLEAN {
        override fun toJsonElement(value: String): JsonPrimitive {
            return JsonPrimitive(value.toBooleanStrict())
        }
    },
    ARRAY {
        override fun toJsonElement(value: String): JsonArray {
            return buildJsonArray {
                Json.decodeFromString<Array<out JsonElement>>(value).forEach {
                    add(it)
                }
            }
        }
    },
    OBJECT {
        override fun toJsonElement(value: String): JsonElement {
            return Json.parseToJsonElement(value)
        }
    },
    JSON_ELEMENT {
        override fun toJsonElement(value: String): JsonElement {
            return Json.parseToJsonElement(value)
        }
    };

    abstract fun toJsonElement(value: String): JsonElement
}