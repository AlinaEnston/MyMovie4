package com.larina.mymovie.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    val title: String,
    val poster: Int,
    val description: String,
    val rating: Float,
    var isInFavorites: Boolean = false
) : Parcelable