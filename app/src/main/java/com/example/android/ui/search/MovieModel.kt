package com.example.android.ui.search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel(
    val id: String?,
    val trackCensoredName: String?,
    val artworkUrl100: String?,
    val trackPrice: String?,
    val genre: String?,
    var isFavoriteMovie: Boolean = false,
    val trackTimeMillis: Long,
    val shortDescription: String,
    val longDescription: String
): Parcelable