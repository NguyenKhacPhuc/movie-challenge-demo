package com.example.android.viewstate

import com.example.domain.model.Movie

data class SearchViewState(
    val isLoading: Boolean,
    val isError: Boolean,
    val result: List<Movie>
)