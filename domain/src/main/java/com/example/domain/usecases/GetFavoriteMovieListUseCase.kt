package com.example.domain.usecases

import com.example.domain.model.Movie
import com.example.domain.repository.FavoriteMovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetFavoriteMovieListUseCase @Inject constructor(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    suspend fun execute(): Flow<List<Movie>> {
        return favoriteMovieRepository.getFavoriteMovieList()
    }
}