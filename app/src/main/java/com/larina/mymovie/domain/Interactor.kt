package com.larina.mymovie.domain

import com.larina.mymovie.data.MainRepository

class Interactor(val repo: MainRepository) {
    fun getFilmsDB(): List<Film> = repo.filmsDataBase
}