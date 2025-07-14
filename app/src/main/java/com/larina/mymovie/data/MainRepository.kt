package com.larina.mymovie.data

import com.larina.mymovie.R
import com.larina.mymovie.domain.Film

class MainRepository {
    internal val filmsDataBase: List<Film> = listOf(
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

    // Метод для получения списка фильмов
    fun getFilms(): List<Film> {
        return filmsDataBase
    }
}
