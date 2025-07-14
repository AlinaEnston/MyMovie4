package com.larina.mymovie.view.fragments

import FilmListRecyclerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.larina.mymovie.R
import com.larina.mymovie.view.rv_adapters.TopSpacingItemDecoration
import com.larina.mymovie.domain.Film
import com.larina.mymovie.MainActivity
import com.larina.mymovie.viewmodel.HomeFragmentViewModel
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var searchView: SearchView

    private var filmsDataBase = listOf<Film>()
        // Используем backing field
        set(value) {
            if (field == value) return
            field = value
            filmsAdapter.updateData(field)
        }

    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeFragmentViewModel::class.java) // Исправлено
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.main_recycler)
        searchView = view.findViewById(R.id.search_view)

        viewModel.filmsListLiveData.observe(viewLifecycleOwner, Observer<List<Film>> {
            filmsDataBase = it
        })

        searchView.setOnClickListener {
            searchView.isIconified = false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Обработка null значения
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    filmsAdapter.updateData(filmsDataBase)
                    return true
                }
                val result = filmsDataBase.filter {
                    it.title.lowercase(Locale.getDefault())
                        .contains(newText.lowercase(Locale.getDefault()))
                }
                filmsAdapter.updateData(result)
                return true
            }
        })

        initRecycler()
        filmsAdapter.updateData(filmsDataBase)
    }

    private fun initRecycler() {
        recyclerView.apply {
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                override fun click(film: Film) {
                    (requireActivity() as MainActivity).launchDetailsFragment(film)
                }
            })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}
