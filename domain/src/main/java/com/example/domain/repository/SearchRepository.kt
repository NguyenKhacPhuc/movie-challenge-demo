package com.example.domain.repository

import com.example.domain.Resource
import com.example.domain.model.MovieListPage


interface SearchRepository {
    suspend fun getSearchResultList(query: String): Resource<MovieListPage>
}