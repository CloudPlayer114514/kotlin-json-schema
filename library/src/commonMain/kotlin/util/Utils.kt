package com.yunbao.kotlin.schema.util

import com.yunbao.kotlin.schema.annotations.ExtraProperty
import kotlinx.serialization.json.JsonElement

fun List<ExtraProperty>.toJsonElements(): Map<String, JsonElement> {
    if (isEmpty()) {
        return emptyMap()
    }
    return buildMap {
        this@toJsonElements.forEach {
            if (it.value.isNotBlank()) {
                this[it.key] = it.jsonType.toJsonElement(it.value)
            }
        }
    }
}
