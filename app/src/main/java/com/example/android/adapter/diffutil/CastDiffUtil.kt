package com.example.android.adapter.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.model.Cast

class CastDiffUtil : DiffUtil.ItemCallback<Cast>() {
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.castId == newItem.castId
    }

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem == newItem
    }

}