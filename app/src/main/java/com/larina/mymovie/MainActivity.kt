package com.larina.mymovie

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.larina.mymovie.databinding.ActivityMainBinding
import com.larina.mymovie.domain.Film
import com.larina.mymovie.view.fragments.DetailsFragment
import com.larina.mymovie.view.fragments.FavoritesFragment
import com.larina.mymovie.view.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Инициализируем объект
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Передаем его в метод
        setContentView(binding.root)

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
        // Здесь не было вызова презентера
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
