package com.gato.movieapp.adapter.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gato.movieapp.R
import com.gato.movieapp.data.database.entities.tv.TopRatedSeeAllTvEntity
import com.gato.movieapp.databinding.ItemRowMovieDescBinding
import com.gato.movieapp.models.TvResult
import com.gato.movieapp.ui.fragment.moviedesc.MovieDescFragmentDirections
import com.gato.movieapp.util.Constants

class TopRatedSeeAllTvAdapter :
    PagingDataAdapter<TopRatedSeeAllTvEntity, TopRatedSeeAllTvAdapter.MyViewHolder>(
        TvComparatorTopRated
    ) {

    class MyViewHolder(private val binding: ItemRowMovieDescBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tv: TopRatedSeeAllTvEntity) {
            binding.movieImage.load(Constants.IMAGE_BASE_URL + tv.posterPath) {
                crossfade(true)
                error(R.drawable.ic_error_placeholder)
            }
            binding.apply {
                movieTitle.text = tv.name
                movieDescription.text = tv.overview
                textViewYearRelease.text = if (tv.firstAirDate.isNotEmpty())
                    tv.firstAirDate.substring(0, 4) else "-"
                movieRateText.text = tv.voteAverage.toString()
                moviePopularityText.text =
                    tv.popularity.toString()
            }

            binding.cardView.setOnClickListener {
                val tvResult = TvResult(
                    id = tv.idTv,
                    name = tv.name,
                    overview = tv.overview,
                    firstAirDate = tv.firstAirDate,
                    voteAverage = tv.voteAverage,
                    popularity = tv.popularity,
                    posterPath = tv.posterPath,
                    backdropPath = tv.backdropPath
                )
                val action =
                    MovieDescFragmentDirections.actionMovieDescFragmentToDetailTvFragment(
                        tvResult
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
        val tv = getItem(position)
        holder.bind(tv!!)

    }
}

object TvComparatorTopRated : DiffUtil.ItemCallback<TopRatedSeeAllTvEntity>() {
    override fun areItemsTheSame(
        oldItem: TopRatedSeeAllTvEntity,
        newItem: TopRatedSeeAllTvEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TopRatedSeeAllTvEntity,
        newItem: TopRatedSeeAllTvEntity
    ): Boolean {
        return oldItem == newItem
    }
}