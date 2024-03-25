package com.qlmat.android.onelab_project.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.qlmat.android.onelab_project.R
import com.qlmat.android.onelab_project.databinding.ItemMovieBinding
import com.qlmat.android.onelab_project.presentation.model.Movie

class PopularMoviesAdapter(
    val onMovieClicked: (Movie) -> Unit
): ListAdapter<Movie, PopularMoviesAdapter.PopularMoviesViewHolder>(moviesDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: PopularMoviesViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val newItem = payloads[0] as Movie
            holder.bind(newItem)
        }
    }

    companion object {
        private val moviesDiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: Movie, newItem: Movie): Any? {
                if (oldItem != newItem) {
                    return newItem
                }
                return super.getChangePayload(oldItem, newItem)
            }
        }
    }

    inner class PopularMoviesViewHolder(
        private val itemBinding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(movie: Movie) = with(itemBinding) {
            title.text = movie.title
            releaseDateValue.text = movie.releaseDate
            descriptionValue.text = movie.overview
            poster.load(
                "https://image.tmdb.org/t/p/original${movie.posterUrl}\""
            ) {
                crossfade(true)
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_launcher_background)
                scale(Scale.FIT)
            }

            itemBinding.root.setOnClickListener {
                onMovieClicked.invoke(movie)
            }
        }
    }
}