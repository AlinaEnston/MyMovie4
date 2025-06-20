package com.larina.mymovie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsFragment : Fragment() {
    private lateinit var detailsDescription: TextView
    private lateinit var detailsPoster: ImageView
    private lateinit var detailsFabShare: FloatingActionButton // Переименуем переменную для ясности
    private lateinit var detailsFabFavorites: FloatingActionButton // Кнопка для избранного
    private lateinit var favoritesDbHelper: FavoritesDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsDescription = view.findViewById(R.id.details_description)
        detailsPoster = view.findViewById(R.id.details_poster)
        detailsFabShare = view.findViewById(R.id.details_fab)
        detailsFabFavorites = view.findViewById(R.id.details_fab_favorites) // Инициализация кнопки для избранного

        // Инициализация базы данных
        favoritesDbHelper = FavoritesDatabaseHelper(requireContext())

        // Получаем объект Film из аргументов
        val film = arguments?.getParcelable<Film>("film")

        film?.let { film ->
            detailsDescription.text = film.description
            detailsPoster.setImageResource(film.poster)

            // Проверяем, есть ли фильм в избранном
            updateFavoriteIcon(film)

            // Устанавливаем обработчик нажатия на кнопку "Поделиться"
            detailsFabShare.setOnClickListener {
                // Создаем интент
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Check out this film: ${film.title} \n\n ${film.description}"
                    )
                    type = "text/plain"
                }
                // Запускаем наше активити
                startActivity(Intent.createChooser(intent, "Share To:"))
            }

            // Устанавливаем обработчик нажатия на кнопку "Избранное"
            detailsFabFavorites.setOnClickListener {
                if (favoritesDbHelper.isFavorite(film.title)) {
                    favoritesDbHelper.removeFavorite(film.title)
                    film.isInFavorites = false
                } else {
                    favoritesDbHelper.addFavorite(film)
                    film.isInFavorites = true
                }
                updateFavoriteIcon(film)
            }
        } ?: run {
            // Обработка случая, если фильм не был передан
            detailsDescription.text = "Описание недоступно"
            detailsPoster.setImageResource(R.drawable.poster_1) // Изображение по умолчанию
        }
    }

    private fun updateFavoriteIcon(film: Film) {
        if (film.isInFavorites) {
            detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_24) // Иконка "в избранном"
        } else {
            detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24) // Иконка "не в избранном"
        }
    }
}
