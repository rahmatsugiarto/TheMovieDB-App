package com.gato.movieapp.adapter.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gato.movieapp.R
import com.gato.movieapp.databinding.ItemRowMovieDescBinding
import com.gato.movieapp.models.MovieResult
import com.gato.movieapp.ui.fragment.search.SearchFragmentDirections
import com.gato.movieapp.util.Constants

class SearchAdapterMovie :
    PagingDataAdapter<MovieResult, SearchAdapterMovie.MyViewHolder>(ComparatorSearch) {

    class MyViewHolder(private val binding: ItemRowMovieDescBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieResult) {
            binding.movieImage.load(Constants.IMAGE_BASE_URL + movie.posterPath) {
                crossfade(true)
                error(R.drawable.ic_error_placeholder)
            }
            binding.apply {
                movieTitle.text = movie.title
                movieDescription.text = movie.overview
                textViewYearRelease.text = if (movie.releaseDate.isNotEmpty() && movie.releaseDate.length > 4)
                    movie.releaseDate.substring(0, 4) else "-"
                movieRateText.text = movie.voteAverage.toString()
                moviePopularityText.text =
                    movie.popularity.toString()
            }

            binding.cardView.setOnClickListener {
                val movieResult = MovieResult(
                    id = movie.id,
                    title = movie.title,
                    overview = movie.overview,
                    releaseDate = movie.releaseDate,
                    voteAverage = movie.voteAverage,
                    popularity = movie.popularity,
                    posterPath = movie.posterPath,
                    backdropPath = movie.backdropPath
                )
                val action =
                    SearchFragmentDirections.actionSearchFragmentToDetailFragmentMovie(
                        movieResult
                    )
                binding.cardView.findNavController().navigate(action)
            }

        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRowMovieDescBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie!!)

    }
}

object ComparatorSearch : DiffUtil.ItemCallback<MovieResult>() {
    override fun areItemsTheSame(
        oldItem: MovieResult,
        newItem: MovieResult
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MovieResult,
        newItem: MovieResult
    ): Boolean {
        return oldItem == newItem
    }
}