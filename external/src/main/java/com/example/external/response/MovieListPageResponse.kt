package com.example.external.response

import com.google.gson.annotations.SerializedName

data class MovieListPageResponse(
    @SerializedName("resultCount") val resultCount: Int?,
    @SerializedName("results") val results: List<MovieResponse>?
)

data class MovieResponse(
    @SerializedName("trackId") val trackId: String?,
    @SerializedName("trackName") val trackName: String?,
    @SerializedName("artworkUrl100") val artworkUrl100: String?,
    @SerializedName("trackPrice") val trackPrice: String?,
    @SerializedName("primaryGenreName") val kind: String?,
    @SerializedName("trackTimeMillis") val trackTimeMillis: String?,
    @SerializedName("shortDescription") val shortDescription: String?,
    @SerializedName("longDescription") val longDescription: String?
)