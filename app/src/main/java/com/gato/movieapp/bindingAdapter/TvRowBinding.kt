package com.gato.movieapp.bindingAdapter


import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.gato.movieapp.R
import com.gato.movieapp.models.TvResult
import com.gato.movieapp.ui.fragment.tv.TvFragmentDirections
import com.gato.movieapp.util.Constants.Companion.IMAGE_BASE_URL

class TvRowBinding {
    companion object {

        @BindingAdapter("onMovieClickListener2")
        @JvmStatic
        fun onMovieClickListener2(rowLayout: ConstraintLayout, tvResult: TvResult) {
            rowLayout.setOnClickListener {
                try {
                    val action = TvFragmentDirections.actionTvFragmentToDetailTvFragment(tvResult)
                    rowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onMovieClickListener", e.message.toString())
                }
            }
        }

    }
}