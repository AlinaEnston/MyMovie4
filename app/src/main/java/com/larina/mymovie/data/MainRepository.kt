import com.larina.mymovie.domain.Film

class MainRepository {
    internal val filmsDataBase: List<Film> = listOf(
        Film(
            title = "Inception",
            poster = "/path/to/inception.jpg",
            description = "A thief who steals corporate secrets through the use of dream-sharing technology.",
            rating = 8.8
        ),
        Film(
            title = "The Dark Knight",
            poster = "/path/to/dark_knight.jpg",
            description = "When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.",
            rating = 9.0
        ),
        Film(
            title = "Interstellar",
            poster = "/path/to/interstellar.jpg",
            description = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
            rating = 8.6
        )
    )

    // Method to get the list of films
    fun getFilms(): List<Film> {
        return filmsDataBase
    }
}
