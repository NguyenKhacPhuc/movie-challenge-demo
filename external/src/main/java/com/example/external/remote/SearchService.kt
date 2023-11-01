package com.example.external.remote

import com.example.external.response.MovieListPageResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchService {
    @POST("search?country=au&media=movie&;all")
    suspend fun getSearchResultList(
        @Query("term") query: String
    ): MovieListPageResponse
}
