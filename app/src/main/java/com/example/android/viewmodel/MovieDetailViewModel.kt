package com.example.android.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.android.ui.search.MovieModel
import com.example.android.viewstate.MovieDetailViewState
import com.example.domain.model.Movie
import com.example.domain.usecases.DeleteFavoriteMovieUseCase
import com.example.domain.usecases.InsertFavoriteMovieUseCase
import javax.inject.Inject
import kotlinx.coroutines.launch

class MovieDetailViewModel @Inject constructor(
    private val insert: InsertFavoriteMovieUseCase,
    private val delete: DeleteFavoriteMovieUseCase
) : BaseViewModel<MovieDetailViewState>() {
    override fun initState(): MovieDetailViewState {
        return MovieDetailViewState(isFavorite = false)
    }

    var isLiked: Boolean = false
        set(value) {
            if (field == value) return
            field = value
            store.dispatchState(newState = currentState.copy(isFavorite = field))
        }

    fun insert(movie: MovieModel) {
        viewModelScope.launch {
            insert.execute(movie.convertToMovie())
        }
    }

    fun delete(movieId: String) {
        viewModelScope.launch {
            delete.execute(movieId)
        }
    }
}

private fun MovieModel.convertToMovie(): Movie {
    return Movie(
        id,
        trackCensoredName,
        artworkUrl100,
        trackPrice,
        genre,
        isFavoriteMovie,
        trackTimeMillis,
        shortDescription,
        longDescription
    )
}
