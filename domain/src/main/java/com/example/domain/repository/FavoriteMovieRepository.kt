package com.example.domain.repository

import com.example.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteMovieRepository {
    suspend fun getFavoriteMovieList(): Flow<List<Movie>>
    suspend fun insertFavoriteMovie(movie: Movie)
    suspend fun deleteFavoriteMovie(movieId: String)
}