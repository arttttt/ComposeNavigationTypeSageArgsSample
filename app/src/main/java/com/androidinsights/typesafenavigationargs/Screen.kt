package com.androidinsights.typesafenavigationargs

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object CatsList : Screen

    @Serializable
    data class CatDetailsWithPrimitives(
        val icon: Int,
        val text: Int,
    ) : Screen

    @Serializable
    data class CatDetails(
        val cat: Cat,
    ) : Screen
}