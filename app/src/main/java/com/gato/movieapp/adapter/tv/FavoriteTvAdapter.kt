package com.gato.movieapp.adapter.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gato.movieapp.R
import com.gato.movieapp.data.database.entities.tv.FavoriteTvEntity
import com.gato.movieapp.databinding.ItemRowMovieDescBinding
import com.gato.movieapp.models.TvResult
import com.gato.movieapp.ui.fragment.favorite.FavoriteFragmentDirections
import com.gato.movieapp.util.Constants
import com.gato.movieapp.util.MyDiffUtil

class FavoriteTvAdapter : RecyclerView.Adapter<FavoriteTvAdapter.MyViewHolder>() {

    private var tvList = emptyList<FavoriteTvEntity>()

    class MyViewHolder(private val binding: ItemRowMovieDescBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tv: TvResult) {
            binding.movieImage.load(Constants.IMAGE_BASE_URL + tv.posterPath) {
                crossfade(true)
                error(R.drawable.ic_error_placeholder)
            }
            binding.apply {
                movieTitle.text = tv.name
                movieDescription.text = tv.overview
                textViewYearRelease.text =
                    tv.firstAirDate.substring(0, 4)
                movieRateText.text = tv.voteAverage.toString()
                moviePopularityText.text =
                    tv.popularity.toString()
            }

            binding.cardView.setOnClickListener {
                val tvResult = TvResult(
                    id = tv.id,
                    name = tv.name,
                    overview = tv.overview,
                    firstAirDate = tv.firstAirDate,
                    voteAverage = tv.voteAverage,
                    popularity = tv.popularity,
                    posterPath = tv.posterPath,
                    backdropPath = tv.backdropPath)
                val action =
                    FavoriteFragmentDirections.actionFavoriteFragmentToDetailTvFragment(
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
        val tv = tvList[position]
        holder.bind(tv.tvResult)
    }

    override fun getItemCount(): Int = tvList.size

    fun setData(newData: List<FavoriteTvEntity>) {
        val diffUtil = MyDiffUtil(tvList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        tvList = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}