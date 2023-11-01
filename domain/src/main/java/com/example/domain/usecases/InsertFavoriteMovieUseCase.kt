package com.example.domain.usecases

import com.example.domain.model.FavoriteMovie
import com.example.domain.model.Movie
import com.example.domain.repository.FavoriteMovieRepository
import javax.inject.Inject

class InsertFavoriteMovieUseCase @Inject constructor(
    private val favoriteMovieRepository: FavoriteMovieRepository
) {
    suspend fun execute(favoriteMovie: Movie){
        return favoriteMovieRepository.insertFavoriteMovie(favoriteMovie)
    }
}