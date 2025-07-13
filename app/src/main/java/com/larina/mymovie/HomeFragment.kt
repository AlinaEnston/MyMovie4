package com.larina.mymovie

import FilmListRecyclerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.larina.mymovie.view.MainActivity
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private lateinit var searchView: SearchView

    private lateinit var filmsDataBase: List<Film>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.main_recycler)
        searchView = view.findViewById(R.id.search_view)

        filmsDataBase = listOf(
            Film(
                "Aladdin",
                R.drawable.poster_8,
                "Aladdin, a kind thief, woos Jasmine, the princess of Agrabah, with the help of Genie. When Jafar, the grand vizier, tries to usurp the king, Jasmine, Aladdin and Genie must stop him from succeeding.",
                7.3f
            ),
            Film(
                "Why Women Kill",
                R.drawable.poster_7,
                "An anthology series that follows three women in different decades all living in the same house, as they deal with infidelity and betrayals in their marriages.",
                8.3f
            ),
            Film(
                "Le fabuleux destin d'Amélie Poulain",
                R.drawable.poster_6,
                "Despite being caught in her imaginative world, young waitress Amelie decides to help people find happiness. Her quest to spread joy leads her on a journey during which she finds true love.",
                8.0f
            ),
            Film(
                "Luck",
                R.drawable.poster_5,
                "The curtain is pulled back on the millennia-old battle between the organizations of good luck and bad luck that secretly affects everyday lives.",
                7.3f
            ),
            Film(
                "Wicked",
                R.drawable.poster_4,
                "Elphaba, a young woman ridiculed for her green skin, and Galinda, a popular girl, become friends at Shiz University in the Land of Oz. After an encounter with the Wonderful Wizard of Oz, their friendship reaches a crossroads.",
                7.0f
            ),
            Film(
                "W.I.T.C.H",
                R.drawable.poster_3,
                "Five teenage girls learn that they have been chosen to guard the walls between parallel universes. For this purpose, they have been given the powers of the elements.",
                6.8f
            ),
            Film(
                "Paddington",
                R.drawable.poster_10,
                "A young Peruvian bear travels to London in search of a home. Finding himself lost and alone at Paddington Station, he meets the kindly Brown family, who offer him a temporary haven.",
                7.3f
            ),
            Film(
                "La La Land",
                R.drawable.poster_1,
                "When Sebastian, a pianist, and Mia, an actress, follow their passion and achieve success in their respective fields, they find themselves torn between their love for each other and their careers.",
                8.0f
            )
        )

        // Обратите внимание: эти методы должны быть внутри класса HomeFragment, но не внутри onViewCreated
        // или onCreateView.  Они должны быть на том же уровне, что и другие методы фрагмента.
        // Также, super.onCreate() и super.onViewCreated() вызываются автоматически, не нужно их вызывать вручную.

        searchView.setOnClickListener {
            searchView.isIconified = false
        }

        //Подключаем слушателя изменений введенного текста в поиска
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            //Этот метод отрабатывает при нажатии кнопки "поиск" на софт клавиатуре
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            //Этот метод отрабатывает на каждое изменения текста
            override fun onQueryTextChange(newText: String): Boolean {
                //Если ввод пуст то вставляем в адаптер всю БД
                if (newText.isEmpty()) {
                    filmsAdapter.updateData(filmsDataBase)
                    return true
                }
                //Фильтруем список на поискк подходящих сочетаний
                val result = filmsDataBase.filter {
                    //Чтобы все работало правильно, нужно и запроси и имя фильма приводить к нижнему регистру
                    it.title.lowercase(Locale.getDefault())
                        .contains(newText.lowercase(Locale.getDefault()))
                }
                //Добавляем в адаптер
                filmsAdapter.updateData(result)
                return true
            }
        })

        //находим наш RV
        initRecyckler()
        //Кладем нашу БД в RV
        filmsAdapter.updateData(filmsDataBase)
    }

    private fun initRecyckler() {
        recyclerView.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
            //Присваиваем адаптер
            adapter = filmsAdapter
            //Присвои layoutmanager
            layoutManager = LinearLayoutManager(requireContext())
            //Применяем декоратор для отступов
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}
