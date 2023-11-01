package com.example.external.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.external.utils.Constants.FAVORITE_MOVIE_TABLE
import com.example.external.utils.Constants.MOVIE_ID
import com.example.external.utils.Constants.MOVIE_IS_LIKED

@Entity(
    tableName = FAVORITE_MOVIE_TABLE
)
class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = MOVIE_ID)
    val movieId: String,
    @ColumnInfo(name = "trackCensoredName")
    val trackCensoredName: String,
    @ColumnInfo(name = "artworkUrl100")
    val artworkUrl100: String,
    @ColumnInfo(name = "trackPrice")
    val trackPrice: String,
    @ColumnInfo(name = "genre")
    val genre: String,
    @ColumnInfo(name = "trackTimeMillis")
    val trackTimeMillis: String,
    @ColumnInfo(name = "shortDescription")
    val shortDescription: String,
    @ColumnInfo(name = "longDescription")
    val longDescription: String,
    @ColumnInfo(name = MOVIE_IS_LIKED)
    val isLiked: Boolean
)