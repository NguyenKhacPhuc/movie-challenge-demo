package com.example.android.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.model.Movie

class MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem && oldItem.isFavoriteMovie == newItem.isFavoriteMovie
    }

}