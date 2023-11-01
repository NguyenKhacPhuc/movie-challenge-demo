package com.example.android.utils

import com.example.domain.model.Genre


fun Long.formatTime(): String {
    val hours = this / 60
    val minutes = this - hours * 60
    return when {
        hours > 0 -> "${hours}h ${minutes}m"
        (hours == 0L && minutes > 0) -> "${minutes}m"
        else -> "" // api result fail
    }
}

fun formatGenresToString(genres: List<Genre>): String {
    return if (genres.isNotEmpty()) {
        genres.joinToString("") { genre ->
            "${genre.name} | "
        }.dropLast(2) // remove last " |"
    } else {
        ""
    }
}