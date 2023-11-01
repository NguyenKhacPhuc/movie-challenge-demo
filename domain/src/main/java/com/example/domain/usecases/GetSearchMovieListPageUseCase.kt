package com.example.domain.usecases

import com.example.domain.Resource
import com.example.domain.model.MovieListPage
import com.example.domain.repository.SearchRepository
import javax.inject.Inject

class GetSearchMovieListPageUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend fun execute(query: String): Resource<MovieListPage> {
        return searchRepository.getSearchResultList(query)
    }
}