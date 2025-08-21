package com.larina.mymovie.utils

import com.larina.mymovie.data.Entity.TmdbFilm
import com.larina.mymovie.domain.Film

class utils {
    object Converter {
        fun convertApiListToDtoList(list: List<TmdbFilm>?): List<Film> {
            val result = mutableListOf<Film>()
            list?.forEach {
                result.add(
                    Film(
                        title = it.title,
                        poster = it.posterPath,
                        description = it.overview,
                        rating = it.voteAverage,
                        isInFavorites = false
                    )
                )
            }
            return result
        }
    }
}