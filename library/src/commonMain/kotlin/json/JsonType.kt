package com.yunbao.kotlin.schema.json

/**
 * 在将字符串解析为 Json 对象时提供的建议。解析时将尝试将字符串中的内容与提供的类型相匹配，如果匹配失败，则将其解析为 JsonElement 。
 *
 * @author CloudPlayer
 */
enum class JsonType {
    Null,
    String,
    Number,
    Boolean,
    Array,
    Object,
    JsonElement,
}