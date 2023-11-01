package com.example.external.mapper

import com.example.domain.model.FavoriteMovie
import com.example.domain.model.Movie
import com.example.external.local.entity.FavoriteMovieEntity
import javax.inject.Inject

class FavoriteMovieMapper @Inject constructor() : Mapper<FavoriteMovieEntity, Movie>() {

    override fun map(response: List<FavoriteMovieEntity>): List<Movie> {
        return response.map { item -> map(item) }
    }

    override fun map(response: FavoriteMovieEntity): Movie {
        return Movie(
            id = response.movieId,
            isFavoriteMovie = response.isLiked,
            trackCensoredName = response.trackCensoredName,
            artworkUrl100 = response.artworkUrl100,
            trackPrice = response.trackPrice,
            genre = response.genre,
            trackTimeMillis = response.trackTimeMillis.toLong(),
            shortDescription = response.shortDescription,
            longDescription = response.longDescription
        )
    }

    fun mapToResponse(response: Movie): FavoriteMovieEntity {
        return FavoriteMovieEntity(
            movieId = response.id ?: "",
            isLiked = response.isFavoriteMovie,
            trackCensoredName = response.trackCensoredName ?: "",
            artworkUrl100 = response.artworkUrl100 ?: "",
            trackPrice = response.trackPrice ?: "",
            genre = response.genre ?: "",
            trackTimeMillis = response.trackTimeMillis.toString(),
            shortDescription = response.shortDescription,
            longDescription = response.longDescription
        )
    }

    fun mapToResponse(response: List<Movie>): List<FavoriteMovieEntity> {
        return response.map { item -> mapToResponse(item) }
    }

}