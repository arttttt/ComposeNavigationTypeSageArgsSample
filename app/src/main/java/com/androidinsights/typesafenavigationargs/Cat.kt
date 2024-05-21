package com.androidinsights.typesafenavigationargs

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class Cat(
    val iconRes: Int,
    val textRes: Int,
)