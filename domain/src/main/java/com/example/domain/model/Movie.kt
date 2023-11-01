package com.example.domain.model

data class Movie(
    val id: String?,
    val trackCensoredName: String?,
    val artworkUrl100: String?,
    val trackPrice: String?,
    val genre: String?,
    var isFavoriteMovie: Boolean = false,
    val trackTimeMillis: Long,
    val shortDescription: String,
    val longDescription: String
)