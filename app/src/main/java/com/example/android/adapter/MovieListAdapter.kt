package com.example.android.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.R
import com.example.android.adapter.diffutil.MovieDiffUtil
import com.example.android.utils.formatTime
import com.example.domain.model.FavoriteMovie
import com.example.domain.model.Movie

class MovieListAdapter(
    private val displayType: MovieItemDisplayType,
    private val onMovieClick: (String) -> Unit,
    private val onStarClick: (FavoriteMovie) -> Unit
) : ListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffUtil()) {

    class MovieGridItemViewHolder(
        view: View,
        private var onMovieClick: (String) -> Unit,
        private var onStarClick: (FavoriteMovie) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private var movie: Movie? = null
        private val movieItemLayout = view.findViewById<ConstraintLayout>(R.id.layoutMovie)
        private val movieNameTextView = view.findViewById<TextView>(R.id.tvMovieName)
        private val movieImageView = view.findViewById<ImageView>(R.id.ivMovieImage)
        private val starImageView = view.findViewById<ImageView>(R.id.ivStar)
        private val movieRunTimeTextView = view.findViewById<TextView>(R.id.tvMovieRunTime)

        init {
            movieItemLayout.setOnClickListener {
                if (movie != null) {
                    movie?.id?.let { id -> onMovieClick.invoke(id) }
                } else {
                    //handle null case
                    Log.e("Error", "MovieGridItemViewHolder #init() movie is null")
                }
            }

            starImageView.setOnClickListener {
                if (movie != null) {
                    if (!movie!!.isFavoriteMovie) { // set icon
                        starImageView.setImageResource(R.drawable.star_active)
                    } else {
                        starImageView.setImageResource(R.drawable.star_default)
                    }
                    val favMovie = FavoriteMovie(movie!!.id, !movie!!.isFavoriteMovie)
                    onStarClick.invoke(favMovie)
                } else {
                    //handle null case
                    Log.e("Error", "MovieGridItemViewHolder #init() movie is null")
                }
            }
        }

        fun bind(movie: Movie) {
            this.movie = movie

            movieNameTextView.text = movie.trackCensoredName
            movieRunTimeTextView.text = movie.trackTimeMillis.formatTime()
            if (movie.isFavoriteMovie) {
                starImageView.setImageResource(R.drawable.star_active)
            } else {
                starImageView.setImageResource(R.drawable.star_default)
            }

            val imageUrl = movie.artworkUrl100
            Glide.with(movieImageView)
                .load(imageUrl)
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_error)
                .into(movieImageView)
        }

    }

    class MovieLinearItemViewHolder(
        view: View,
        private var onMovieClick: (String) -> Unit,
        private var onStarClick: (FavoriteMovie) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private var movie: Movie? = null
        private val movieLayout = view.findViewById<ConstraintLayout>(R.id.layoutMovie)
        private val movieNameTextView = view.findViewById<TextView>(R.id.tvMovieName)
        private val movieDescriptionTextView = view.findViewById<TextView>(R.id.tvMovieDescription)
        private val movieImageView = view.findViewById<ImageView>(R.id.ivMovieImage)
        private val starImageView = view.findViewById<ImageView>(R.id.ivStar)

        init {
            movieLayout.setOnClickListener {
                if (movie != null) {
                    onMovieClick.invoke(movie?.id ?: "")
                } else {
                    //handle null case
                    Log.e("Error", "MovieLinearItemViewHolder #init() movie is null")
                }
            }

            starImageView.setOnClickListener {
                if (movie != null) {
                    if (!movie!!.isFavoriteMovie) { // set icon
                        starImageView.setImageResource(R.drawable.star_active)
                    } else {
                        starImageView.setImageResource(R.drawable.star_default)
                    }
                    val favMovie = FavoriteMovie(movie!!.id, !movie!!.isFavoriteMovie)
                    onStarClick.invoke(favMovie)
                } else {
                    //handle null case
                    Log.d("CongTV5", "MovieLinearItemViewHolder #init() movie is null")
                }
            }
        }

        fun bind(movie: Movie) {
            this.movie = movie

            if (movie.isFavoriteMovie) {
                starImageView.setImageResource(R.drawable.star_active)
            } else {
                starImageView.setImageResource(R.drawable.star_default)
            }

            movieNameTextView.text = movie.trackCensoredName
            movieDescriptionTextView.text = movie.shortDescription

            val imageUrl = movie.artworkUrl100
            Glide.with(movieImageView)
                .load(imageUrl)
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_error)
                .into(movieImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (displayType) {
            MovieItemDisplayType.GRID -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.grid_display_movie_item_layout, parent, false)
                MovieGridItemViewHolder(view, onMovieClick, onStarClick)
            }
            MovieItemDisplayType.VERTICAL_LINEAR -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.linear_display_movie_item_layout, parent, false)
                MovieLinearItemViewHolder(view, onMovieClick, onStarClick)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (displayType) {
            MovieItemDisplayType.GRID -> {
                (holder as? MovieGridItemViewHolder)?.bind(getItem(position))
            }
            MovieItemDisplayType.VERTICAL_LINEAR -> {
                (holder as? MovieLinearItemViewHolder)?.bind(getItem(position))
            }
        }
    }

    override fun submitList(list: MutableList<Movie>?) {
        super.submitList(list)
    }
}
enum class MovieItemDisplayType {
    GRID,
    VERTICAL_LINEAR
}
