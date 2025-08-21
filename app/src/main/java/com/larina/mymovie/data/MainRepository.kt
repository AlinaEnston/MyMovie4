package com.larina.mymovie.data

import com.larina.mymovie.R
import com.larina.mymovie.domain.Film

class MainRepository {
    internal val filmsDataBase: List<Film> = listOf(

    )

    // Метод для получения списка фильмов
    fun getFilms(): List<Film> {
        return filmsDataBase
    }
}
