package com.gato.movieapp.adapter.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gato.movieapp.databinding.ItemRowMoviesBinding
import com.gato.movieapp.models.MoviesResponse
import com.gato.movieapp.models.MovieResult
import com.gato.movieapp.util.MyDiffUtil

class TopRatedMoviesAdapter : RecyclerView.Adapter<TopRatedMoviesAdapter.MyViewHolder>() {

    private var moviesList = emptyList<MovieResult>()

    class MyViewHolder(private val binding: ItemRowMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieResult) {
            binding.result = movie
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRowMoviesBinding.inflate(layoutInflater, parent, false)
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
        val movie = moviesList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = moviesList.size

    fun setData(newData: MoviesResponse) {
        val diffUtil = MyDiffUtil(moviesList, newData.movieResults)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        moviesList = newData.movieResults
        diffUtilResult.dispatchUpdatesTo(this)
    }
}