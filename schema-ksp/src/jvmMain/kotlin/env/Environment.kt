package com.yunbao.kotlin.schema.env

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.PlatformInfo
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment


object Environment {
    lateinit var log: KSPLogger
        private set

    lateinit var kotlinVersion: KotlinVersion
        private set

    lateinit var options: Map<String, String>
        private set

    lateinit var codeGenerator: CodeGenerator
        private set

    lateinit var platforms: List<PlatformInfo>
        private set

    private fun refreshEnvironment(environment: SymbolProcessorEnvironment) {
        environment.logger.info("Initializing Ktor AI KSP environment")
        log = environment.logger
        kotlinVersion = environment.kotlinVersion
        options = environment.options
        codeGenerator = environment.codeGenerator
        platforms = environment.platforms
        log.info("Ktor AI KSP environment initialized")
    }

    operator fun invoke(environment: SymbolProcessorEnvironment) {
        refreshEnvironment(environment)
    }
}