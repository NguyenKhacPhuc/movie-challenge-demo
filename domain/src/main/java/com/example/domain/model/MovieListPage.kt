package com.example.domain.model

data class MovieListPage(
    val resultCount: Int,
    val results: List<Movie>
)