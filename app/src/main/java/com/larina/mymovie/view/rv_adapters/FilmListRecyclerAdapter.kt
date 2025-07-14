import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.larina.mymovie.domain.Film
import com.larina.mymovie.databinding.FilmItemBinding


class FilmListRecyclerAdapter(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FilmViewHolder>() {

    private var films: MutableList<Film> = mutableListOf()

    interface OnItemClickListener {
        fun click(film: Film)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = FilmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(binding, onItemClickListener)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(films[position])
    }

    override fun getItemCount(): Int = films.size

    fun updateData(newFilms: List<Film>) {
        films.clear()
        films.addAll(newFilms)
        notifyDataSetChanged()
    }
}