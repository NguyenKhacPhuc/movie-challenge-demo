package com.example.external.provider

import com.example.domain.di.DefaultDispatcher
import com.example.domain.model.Movie
import com.example.domain.repository.FavoriteMovieRepository
import com.example.external.local.FavoriteMovieDao
import com.example.external.mapper.FavoriteMovieMapper
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class FavoriteMovieRepositoryProvider @Inject constructor(
    private val favoriteMovieDao: FavoriteMovieDao,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val favoriteMovieMapper: FavoriteMovieMapper,
) : FavoriteMovieRepository {

    override suspend fun getFavoriteMovieList(): Flow<List<Movie>> {
        return withContext(defaultDispatcher) {
            favoriteMovieDao.getFavoriteMovie().map { list ->
                favoriteMovieMapper.map(list)
            }
        }
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        withContext(defaultDispatcher) {
            favoriteMovieDao.insertMovie(favoriteMovieMapper.mapToResponse(movie))
        }
    }

    override suspend fun deleteFavoriteMovie(movieId: String) {
        withContext(defaultDispatcher) {
            favoriteMovieDao.deleteByMovieId(movieId)
        }
    }

}