package com.example.android.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.adapter.diffutil.MovieDiffUtil
import com.example.android.ui.search.MovieModel
import com.example.domain.model.Movie

class SearchResultListAdapter(
    private val onMovieClick: (MovieModel) -> Unit
) : ListAdapter<Movie, SearchResultListAdapter.SearchResultViewHolder>(MovieDiffUtil()) {

    class SearchResultViewHolder(
        view: View,
        private var onMovieClick: (MovieModel) -> Unit,
    ) : RecyclerView.ViewHolder(view) {

        private var movie: Movie? = null
        private val movieNameTextView = view.findViewById<TextView>(R.id.movieName)
        private val movieImageView = view.findViewById<ImageView>(R.id.moviePoster)
        private val movieGenre = view.findViewById<TextView>(R.id.genre)
        private val movieShortDescription = view.findViewById<TextView>(R.id.shortDescription)
        private val moviePrice = view.findViewById<TextView>(R.id.moviePrice)

        init {
            view.setOnClickListener {
                if (movie != null) {
                    onMovieClick.invoke(
                        MovieModel(
                            id = movie!!.id,
                            trackCensoredName = movie!!.trackCensoredName,
                            artworkUrl100 = movie!!.artworkUrl100,
                            genre = movie!!.genre,
                            trackPrice = movie!!.trackPrice,
                            isFavoriteMovie = movie!!.isFavoriteMovie,
                            trackTimeMillis = movie!!.trackTimeMillis,
                            shortDescription = movie!!.shortDescription,
                            longDescription = movie!!.longDescription
                        )
                    )
                } else {
                    //handle null case
                    Log.e("Error", "SearchResultViewHolder #init() movie is null")
                }
            }
        }

        fun bind(movie: Movie) {
            this.movie = movie
            movieNameTextView.text = movie.trackCensoredName
            movieGenre.text = movie.genre
            movieShortDescription.text = movie.shortDescription
            moviePrice.text = movie.trackPrice

            val imageUrl = movie.artworkUrl100
            Glide.with(movieImageView)
                .load(imageUrl)
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_error)
                .into(movieImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result_item_layout, parent, false)
        return SearchResultViewHolder(view, onMovieClick)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }



}