import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.larina.mymovie.Film
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


class FilmViewHolder(
    private val binding: FilmItemBinding,
    private val onItemClickListener: FilmListRecyclerAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(film: Film) {
        binding.title.text = film.title
        Glide.with(binding.root)
            .load(film.poster)
            .centerCrop()
            .into(binding.poster)
        binding.description.text = film.description
        binding.ratingDonut.progress = (film.rating * 10).toInt()


        // Set the click listener for the item
        itemView.setOnClickListener {
            onItemClickListener.click(film)
        }
    }
}
