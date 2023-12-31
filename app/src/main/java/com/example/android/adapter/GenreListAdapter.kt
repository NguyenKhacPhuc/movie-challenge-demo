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
import com.example.domain.model.Genre
import com.example.android.adapter.diffutil.GenreDiffUtil

class GenreListAdapter(
    private val onClickGenre: (Int, String) -> Unit
) : ListAdapter<Genre, GenreListAdapter.GenreViewHolder>(GenreDiffUtil()) {

    class GenreViewHolder(
        view: View, private val onClickGenre: (Int, String) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        var genre: Genre? = null

        private val genreTitleTextView = view.findViewById<TextView>(R.id.tvGenreItemTitle)
        private val genreImageView = view.findViewById<ImageView>(R.id.ivGenreItemImage)
        private val genreItemLayout = view.findViewById<ConstraintLayout>(R.id.layoutGenreItem)

        init {
            genreItemLayout.setOnClickListener {
                if (genre != null) {
                    onClickGenre.invoke(genre!!.id, genre!!.name)
                } else {
                    Log.e("Error", "GenreViewHolder #init genre value is null")
                }
            }
        }

        fun bind(genre: Genre) {
            this.genre = genre
            genreTitleTextView.text = genre.name

            val imageUrl = genre.backdropPath

            Glide.with(genreImageView)
                .load(imageUrl)
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_error)
                .into(genreImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.genre_item_layout, parent, false)
        return GenreViewHolder(view, onClickGenre)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}