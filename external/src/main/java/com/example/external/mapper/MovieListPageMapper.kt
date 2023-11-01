package com.example.external.mapper

import com.example.domain.model.Movie
import com.example.domain.model.MovieListPage
import com.example.external.response.MovieListPageResponse
import com.example.external.response.MovieResponse
import javax.inject.Inject

class MovieListPageMapper @Inject constructor(
    private val movieMapper: MovieMapper
) : Mapper<MovieListPageResponse, MovieListPage>() {

    override fun map(response: List<MovieListPageResponse>): List<MovieListPage> {
        return response.map { item -> map(item) }
    }

    override fun map(response: MovieListPageResponse): MovieListPage {
        return MovieListPage(
            resultCount = response.resultCount ?: 0,
            results = response.results?.let { movieMapper.map(it) } ?: listOf()
        )
    }

}

class MovieMapper @Inject constructor() : Mapper<MovieResponse, Movie>() {

    override fun map(response: List<MovieResponse>): List<Movie> {
        return response.map { item -> map(item) }
    }

    override fun map(response: MovieResponse): Movie {
        return Movie(
            id = response.trackId,
            trackCensoredName = response.trackName,
            artworkUrl100 = response.artworkUrl100,
            trackPrice = response.trackPrice,
            genre = response.kind,
            trackTimeMillis = response.trackTimeMillis?.toLong() ?: 0,
            shortDescription = response.shortDescription ?: "",
            longDescription = response.longDescription ?: ""
        )
    }

}