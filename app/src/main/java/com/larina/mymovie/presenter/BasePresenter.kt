package com.larina.mymovie.presenter

import android.app.Activity

interface BasePresenter {
    fun attachPresenter(activity: Activity)
    fun getListFromDB()
}