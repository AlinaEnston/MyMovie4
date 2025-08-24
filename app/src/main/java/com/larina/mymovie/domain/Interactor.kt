package com.larina.mymovie.domain

import com.larina.mymovie.data.Entity.API
import com.larina.mymovie.data.Entity.TmdbApi
import com.larina.mymovie.data.Entity.TmdbResultsDto
import com.larina.mymovie.data.MainRepository
import com.larina.mymovie.utils.utils.Converter
import com.larina.mymovie.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi) {
    // В конструктор мы будем передавать коллбэк из вью модели, чтобы реагировать на то, когда фильмы будут получены
    // и страницу, которую нужно загрузить (это для пагинации)
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        // Access the API key from ApiConstants
        retrofitService.getFilms(API.ApiConstants.KEY, "ru-RU", page).enqueue(object : Callback<TmdbResultsDto> {
            override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                // При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                callback.onSuccess(Converter.convertApiListToDtoList(response.body()?.tmdbFilms))
            }

            override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                // В случае провала вызываем другой метод
                callback.onFailure()
            }
        })
    }
}
