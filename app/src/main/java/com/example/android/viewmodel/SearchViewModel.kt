package com.example.android.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.android.viewstate.SearchViewState
import com.example.domain.Resource
import com.example.domain.model.MovieListPage
import com.example.domain.usecases.GetFavoriteMovieListUseCase
import com.example.domain.usecases.GetSearchMovieListPageUseCase
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SearchViewModel @Inject constructor(
    private val getFavoriteMovieListUseCase: GetFavoriteMovieListUseCase,
    private val getSearchMovieListPageUseCase: GetSearchMovieListPageUseCase
) : BaseViewModel<SearchViewState>() {

    private var loadMovieListResult: Job? = null

    init {
        getFavoritesMovie()
    }

    override fun initState(): SearchViewState {
        return SearchViewState(
            isLoading = false,
            isError = false,
            result = listOf()
        )
    }

    fun search(query: String) {
        setIsError(false)
        handleLoadingState(true)
        viewModelScope.launch {
            when (val moviesResource =
                getSearchMovieListPageUseCase.execute(
                    query = query
                )) {
                is Resource.Success -> {
                    moviesResource.data?.let { newList ->
                        addResultListPage(newList)
                    }
                }

                is Resource.Error -> {
                    setIsError(true)
                }
            }
            handleLoadingState(false)
        }
    }

    fun getFavoritesMovie() {
        viewModelScope.launch {
            getFavoriteMovieListUseCase.execute().onStart {
                handleLoadingState(true)
            }.onCompletion { handleLoadingState(false) }.collect {
                handleLoadingState(false)
                store.dispatchState(newState = currentState.copy(result = it))
            }
        }
    }

    private fun addResultListPage(newList: MovieListPage) {
        store.dispatchState(newState = currentState.copy(result = newList.results))
    }


    private fun handleLoadingState(isLoading: Boolean) {
        setIsLoading(isLoading)
    }

    private fun setIsLoading(value: Boolean) {
        store.dispatchState(newState = currentState.copy(isLoading = value))
    }

    private fun setIsError(value: Boolean) {
        store.dispatchState(newState = currentState.copy(isError = value))
    }

    fun clearResult() {
        store.dispatchState(
            newState = currentState.copy(
                result = mutableListOf()
            )
        )
    }

    override fun onCleared() {
        loadMovieListResult?.cancel()
        super.onCleared()
    }
}