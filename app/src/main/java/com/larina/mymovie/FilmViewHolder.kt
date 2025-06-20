package com.larina.mymovie

import android.R.attr.title
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.larina.mymovie.databinding.FilmItemBinding

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

        binding.root.setOnClickListener {
            onItemClickListener.click(film)
        }
    }
}