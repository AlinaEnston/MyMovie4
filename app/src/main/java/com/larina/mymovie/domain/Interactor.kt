package com.larina.mymovie.domain

import MainRepository
import android.util.Log
import com.larina.mymovie.data.Entity.API
import com.larina.mymovie.data.Entity.TmdbApi
import com.larina.mymovie.data.Entity.TmdbResultsDto
import com.larina.mymovie.utils.utils.Converter
import com.larina.mymovie.viewmodel.HomeFragmentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi) {
    // В конструктор мы будем передавать коллбэк из вью модели, чтобы реагировать на то, когда фильмы будут получены
    // и страницу, которую нужно загрузить (это для пагинации)
    fun getFilmsFromApi(page: Int, callback: HomeFragmentViewModel.ApiCallback) {
        retrofitService.getFilms(API.ApiConstants.KEY, "ru-RU", page).enqueue(object : Callback<TmdbResultsDto> {
            override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                if (response.isSuccessful && response.body() != null) {
                    val films = Converter.convertApiListToDtoList(response.body()!!.tmdbFilms)
                    Log.d("API Response", "Fetched films: $films")
                    callback.onSuccess(films)
                } else {
                    Log.e("API Error", "Response not successful: ${response.errorBody()?.string()}")
                    callback.onFailure()
                }
            }


            override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                Log.e("Interactor", "Network error: ${t.message}")
                callback.onFailure()
            }
        })
    }
}
