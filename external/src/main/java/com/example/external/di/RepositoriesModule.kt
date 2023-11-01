package com.example.external.di

import com.example.domain.repository.FavoriteMovieRepository
import com.example.domain.repository.SearchRepository
import com.example.external.provider.FavoriteMovieRepositoryProvider
import com.example.external.provider.SearchRepositoryProvider
import dagger.Binds
import dagger.Module

@Module
@Suppress("UNUSED")
abstract class RepositoriesModule {

    @Binds
    abstract fun bindSearchRepository(loginProvider: SearchRepositoryProvider): SearchRepository

    @Binds
    abstract fun bindFavoriteMovieRepository(favoriteMovieProvider: FavoriteMovieRepositoryProvider): FavoriteMovieRepository
}
