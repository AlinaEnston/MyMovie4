package com.larina.mymovie.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.larina.mymovie.DetailsFragment
import com.larina.mymovie.FavoritesFragment
import com.larina.mymovie.Film
import com.larina.mymovie.HomeFragment
import com.larina.mymovie.R
import com.larina.mymovie.data.AppDataBase // Импортируем вашу реализацию базы данных
import com.larina.mymovie.databinding.ActivityMainBinding
import com.larina.mymovie.presenter.BasePresenter // Импортируем интерфейс BasePresenter
import com.larina.mymovie.presenter.MainPresenter // Импортируем презентер

class MainActivity : AppCompatActivity(), BaseView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var presenter: BasePresenter // Создаем переменную под наш презентер

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Инициализируем объект
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Передаем его в метод
        setContentView(binding.root)

        // Инициализируем базу данных и презентер
        presenter = MainPresenter(AppDataBase()) // Создаем экземпляр MainPresenter
        presenter.attachPresenter(this) // Привязываем презентер к активности

        bottomNavigationView = binding.bottomNavigation // Используем binding для доступа к bottomNavigation
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorites -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_placeholder, FavoritesFragment())
                        .addToBackStack(null)
                        .commit()
                    true
                }
                R.id.watch_later -> {
                    Toast.makeText(this, "Посмотреть позже", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // Устанавливаем начальный фрагмент
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_placeholder, HomeFragment())
            .commit()

        // Обработка кнопки назад с диалогом подтверждения
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fm = supportFragmentManager
                if (fm.backStackEntryCount > 0) {
                    fm.popBackStack()
                } else {
                    Toast.makeText(this@MainActivity, "Выход", Toast.LENGTH_SHORT).show()
                    showExitConfirmationDialog()
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        // Передаем в презентер ссылку на наше активити
        presenter.attachPresenter(this)
        // Вызываем метод презентера, чтобы получить от него наш список из БД
        presenter.getListFromDB()
    }

    override fun setListForView(list: List<String>) {
        val string = list.joinToString(separator = "\n") // Объединяем список в одну строку с переносами
        binding.textView.text = string // Теперь у нас есть доступ к textView
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Выход из приложения")
            .setMessage("Вы уже уходите?")
            .setPositiveButton("Да") { _, _ ->
                finish()
            }
            .setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton("Не знаю") { _, _ ->
                Toast.makeText(this, "Оставайся", Toast.LENGTH_SHORT).show()
            }
            .show()
            .setCancelable(true)
    }

    fun launchDetailsFragment(film: Film) {
        val detailsFragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        detailsFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_placeholder, detailsFragment)
            .addToBackStack(null)
            .commit()
    }
}
