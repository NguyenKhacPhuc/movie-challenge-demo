package com.example.domain.usecases

import com.example.domain.repository.FavoriteMovieRepository
import javax.inject.Inject

class DeleteFavoriteMovieUseCase@Inject constructor(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    suspend fun execute(movieId: String) {
        return favoriteMovieRepository.deleteFavoriteMovie(movieId)
    }
}