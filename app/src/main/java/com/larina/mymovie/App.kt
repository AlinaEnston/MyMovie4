package com.larina.mymovie

import android.app.Application
import com.larina.mymovie.data.AppDataBase
import com.larina.mymovie.data.BaseDataBase
import com.larina.mymovie.presenter.BasePresenter
import com.larina.mymovie.presenter.MainPresenter

class App: Application() {
    //Переменные для наших компонентов
    lateinit var dataBase: BaseDataBase
    lateinit var mainPresenter: BasePresenter

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Инициализируем наши компоненты
        dataBase = AppDataBase()
        mainPresenter = MainPresenter(dataBase)
    }
    //Здесь у нас экземпляр нашего класса Application, через который у нас будет доступ к нашим компонентам
    companion object {
        lateinit var instance: App
            private set
    }
}