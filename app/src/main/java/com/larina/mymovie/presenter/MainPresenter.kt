package com.larina.mymovie.presenter

import android.app.Activity
import com.larina.mymovie.view.MainActivity
import com.larina.mymovie.data.BaseDataBase

class MainPresenter(private val dataBase: BaseDataBase) : BasePresenter {
    //Здесь будет ссылка на наше активити
    private lateinit var mainActivity: MainActivity
    //Этот метод будет вызываться в активити, и в параметр будет передаваться ссылка на активити через this
    override fun attachPresenter(activity: Activity) {
        mainActivity = activity as MainActivity
    }

    override fun getListFromDB() {
        val list = dataBase.returnBase()
        mainActivity.setListForView(list)
    }
}