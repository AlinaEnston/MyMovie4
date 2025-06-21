package com.larina.mymovie

import FilmListRecyclerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesFragment : Fragment() {

    private lateinit var favoritesRecycler: RecyclerView
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var favoritesDatabaseHelper: FavoritesDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoritesDatabaseHelper = FavoritesDatabaseHelper(requireContext())
        favoritesRecycler = view.findViewById(R.id.favorites_recycler)

        // Получите список избранных фильмов из базы данных
        val favoriteFilmsList = favoritesDatabaseHelper.getAllFavorites()

        // Инициализируйте адаптер с слушателем кликов (NO DATA HERE!)
        filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
            override fun click(film: Film) {
                (requireActivity() as MainActivity).launchDetailsFragment(film)
            }
        })

        // Настройте RecyclerView
        favoritesRecycler.apply {
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }

        // Set the data on the adapter AFTER creating it
        filmsAdapter.updateData(favoriteFilmsList)
    }

    override fun onResume() {
        super.onResume()
        // Обновите данные при возвращении в фрагмент
        val updatedList = favoritesDatabaseHelper.getAllFavorites()
        filmsAdapter.updateData(updatedList)
    }
}
