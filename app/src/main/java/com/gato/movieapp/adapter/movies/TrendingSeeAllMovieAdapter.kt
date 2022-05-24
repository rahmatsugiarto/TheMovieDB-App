package com.gato.movieapp.adapter.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gato.movieapp.R
import com.gato.movieapp.data.database.entities.movie.TrendingSeeAllMovieEntity
import com.gato.movieapp.databinding.ItemRowMovieDescBinding
import com.gato.movieapp.models.MovieResult
import com.gato.movieapp.ui.fragment.moviedesc.MovieDescFragmentDirections
import com.gato.movieapp.util.Constants


class TrendingSeeAllMovieAdapter :
    PagingDataAdapter<TrendingSeeAllMovieEntity, TrendingSeeAllMovieAdapter.MyViewHolder>(
        MovieComparatorTrending
    ) {

    class MyViewHolder(private val binding: ItemRowMovieDescBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: TrendingSeeAllMovieEntity) {
            binding.movieImage.load(Constants.IMAGE_BASE_URL + movie.posterPath) {
                crossfade(true)
                error(R.drawable.ic_error_placeholder)
            }
            binding.apply {
                movieTitle.text = movie.title
                movieDescription.text = movie.overview
                textViewYearRelease.text = if (movie.releaseDate.isNotEmpty())
                    movie.releaseDate.substring(0, 4) else "-"
                movieRateText.text = movie.voteAverage.toString()
                moviePopularityText.text =
                    movie.popularity.toString()
            }

            binding.cardView.setOnClickListener {
                val movieResult = MovieResult(
                    id = movie.idMovie,
                    title = movie.title,
                    overview = movie.overview,
                    releaseDate = movie.releaseDate,
                    voteAverage = movie.voteAverage,
                    popularity = movie.popularity,
                    posterPath = movie.posterPath,
                    backdropPath = movie.backdropPath
                )
                val action =
                    MovieDescFragmentDirections.actionSeeAllMovieFragmentToDetailFragmentMovie(
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

object MovieComparatorTrending : DiffUtil.ItemCallback<TrendingSeeAllMovieEntity>() {
    override fun areItemsTheSame(
        oldItem: TrendingSeeAllMovieEntity,
        newItem: TrendingSeeAllMovieEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TrendingSeeAllMovieEntity,
        newItem: TrendingSeeAllMovieEntity
    ): Boolean {
        return oldItem == newItem
    }
}