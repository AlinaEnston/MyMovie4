package com.larina.mymovie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.larina.mymovie.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoritesDbHelper: FavoritesDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Инициализация View Binding
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация базы данных
        favoritesDbHelper = FavoritesDatabaseHelper(requireContext())

        // Получаем объект Film из аргументов
        val film = arguments?.getParcelable<Film>("film")

        film?.let { film ->
            // Устанавливаем данные фильма в интерфейс через Data Binding
            binding.film = film
            binding.executePendingBindings() // Обновляем интерфейс

            // Устанавливаем обработчик нажатия на кнопку "Поделиться"
            binding.detailsFab.setOnClickListener {
                shareFilm(film)
            }

            // Устанавливаем обработчик нажатия на кнопку "Избранное"
            binding.detailsFabFavorites.setOnClickListener {
                toggleFavorite(film)
            }
        } ?: run {
            // Обработка случая, если фильм не был передан
            binding.detailsDescription.text = "Описание недоступно"
            binding.detailsPoster.setImageResource(R.drawable.poster_1) // Изображение по умолчанию
        }
    }

    private fun shareFilm(film: Film) {
        // Создаем интент для совместного использования
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Check out this film: ${film.title} \n\n ${film.description}"
            )
            type = "text/plain"
        }
        // Запускаем активити для выбора приложения для совместного использования
        startActivity(Intent.createChooser(intent, "Share To:"))
    }

    private fun toggleFavorite(film: Film) {
        // Логика для добавления/удаления фильма из избранного
        if (favoritesDbHelper.isFavorite(film.title)) {
            favoritesDbHelper.removeFavorite(film.title)
            film.isInFavorites = false
        } else {
            favoritesDbHelper.addFavorite(film)
            film.isInFavorites = true
        }
        updateFavoriteIcon(film)
    }

    private fun updateFavoriteIcon(film: Film) {
        // Обновление иконки в зависимости от статуса избранного
        if (film.isInFavorites) {
            binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_24) // Иконка "в избранном"
        } else {
            binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24) // Иконка "не в избранном"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Освобождаем binding, чтобы избежать утечек памяти
    }
}
   