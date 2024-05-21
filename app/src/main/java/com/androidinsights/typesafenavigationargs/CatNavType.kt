package com.androidinsights.typesafenavigationargs

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

data object CatNavType : NavType<Cat>(
    isNullableAllowed = true,
) {

    override fun parseValue(value: String): Cat {
        return Json.decodeFromString(Cat.serializer(), value)
    }

    override fun serializeAsValue(value: Cat): String {
        return Json.encodeToString(Cat.serializer(), value)
    }

    override fun get(bundle: Bundle, key: String): Cat? {
        val data = bundle.getString(key)

        data ?: return null

        return parseValue(data)
    }

    override fun put(bundle: Bundle, key: String, value: Cat) {
        bundle.putString(key, serializeAsValue(value))
    }
}