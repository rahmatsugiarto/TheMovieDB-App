package com.gato.movieapp.bindingAdapter


import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.gato.movieapp.R
import com.gato.movieapp.models.MovieResult
import com.gato.movieapp.ui.fragment.movie.MovieFragmentDirections
import com.gato.movieapp.util.Constants.Companion.IMAGE_BASE_URL

class MovieRowBinding {
    companion object {

        @BindingAdapter("onMovieClickListener")
        @JvmStatic
        fun onMovieClickListener(rowLayout: ConstraintLayout, movieResult: MovieResult) {
            rowLayout.setOnClickListener {
                try {
                    val action = MovieFragmentDirections.actionMovieFragmentToDetailFragmentMovie(movieResult)
                    rowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onMovieClickListener", e.message.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {
            imageView.load(IMAGE_BASE_URL + imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

    }
}