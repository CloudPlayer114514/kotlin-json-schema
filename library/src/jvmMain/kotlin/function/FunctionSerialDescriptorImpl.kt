package com.yunbao.kotlin.schema.function

import kotlin.reflect.KFunction

internal data class FunctionSerialDescriptorImpl<out F : KFunction<R>, out R>(
    override val function: F,
) : FunctionSerialDescriptor<F, R>