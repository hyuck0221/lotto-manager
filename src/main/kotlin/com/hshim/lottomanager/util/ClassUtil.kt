package com.hshim.lottomanager.util

import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

object ClassUtil {
    inline fun <reified T: Any> Map<String, Any?>.toClass(): T {
        val instance = T::class.constructors.firstOrNull { it.parameters.isEmpty() }?.call()
            ?: throw IllegalArgumentException("클래스 ${T::class}에 기본 생성자가 없습니다.")

        this.forEach { (key, value) ->
            val property = T::class.memberProperties.find { it.name == key }
                ?: throw IllegalArgumentException("프로퍼티 $key 가 클래스 ${T::class}에 존재하지 않습니다.")

            val setter = T::class.memberFunctions.find { it.name == "set${key.capitalize()}" }
            if (setter != null) {
                setter.call(instance, value)
            } else {
                property.javaField?.isAccessible = true
                property.javaField?.set(instance, value)
            }
        }
        return instance
    }
}