package com.gato.movieapp.bindingAdapter

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gato.movieapp.models.DetailMovieResponse
import com.gato.movieapp.models.DetailTvResponse

@SuppressLint("SetTextI18n")
class DetailBinding {
    companion object {

        @BindingAdapter("setTextGenre")
        @JvmStatic
        fun setTextGenre(textView: TextView, detail: DetailMovieResponse?) {
            textView.text = detail?.genres?.joinToString(", ") { it.name }
        }

        @BindingAdapter("setTextProductionCompanies")
        @JvmStatic
        fun setTextProductionCompanies(textView: TextView, detail: DetailMovieResponse?) {
            textView.text = detail?.productionCompanies?.joinToString(", ") { it.name }
        }

        @BindingAdapter("setTextRuntime")
        @JvmStatic
        fun setTextRuntime(textView: TextView, detail: DetailMovieResponse?) {
            textView.text = detail?.runtime.toString() + " min"
        }

        @BindingAdapter("setTextReleaseDate")
        @JvmStatic
        fun setTextReleaseDate(textView: TextView, detail: DetailMovieResponse?) {
            val releaseDate = detail?.releaseDate
            Log.d("releaseDate", "setTextReleaseDate: $releaseDate")
            if (releaseDate != null && releaseDate.length >= 10) {
                val releaseDateSplit = releaseDate.split("-")
                val releaseDateMonth = releaseDateSplit[1]
                val releaseDateYear = releaseDateSplit[0]
                val releaseDateDay = releaseDateSplit[2]
                val releaseDateMonthName = getMonthName(releaseDateMonth.toInt())
                textView.text = "$releaseDateDay $releaseDateMonthName $releaseDateYear"
            } else {
                textView.text = "-"
            }
        }

        @BindingAdapter("setTextGenre2")
        @JvmStatic
        fun setTextGenre2(textView: TextView, detail: DetailTvResponse?) {
            textView.text = detail?.genres?.joinToString(", ") { it.name }
        }

        @BindingAdapter("setTextProductionCompanies2")
        @JvmStatic
        fun setTextProductionCompanies2(textView: TextView, detail: DetailTvResponse?) {
            textView.text = detail?.productionCompanies?.joinToString(", ") { it.name }
        }

//        @BindingAdapter("setTextRuntime2")
//        @JvmStatic
//        fun setTextRuntime2(textView: TextView, detail: DetailTvResponse?) {
//            textView.text = detail?..toString() + " min"
//        }

        @BindingAdapter("setTextReleaseDate2")
        @JvmStatic
        fun setTextReleaseDate2(textView: TextView, detail: DetailTvResponse?) {
            val releaseDate = detail?.firstAirDate
            Log.d("releaseDate", "setTextReleaseDate: $releaseDate")
            if (releaseDate != null && releaseDate.length >= 10) {
                val releaseDateSplit = releaseDate.split("-")
                val releaseDateMonth = releaseDateSplit[1]
                val releaseDateYear = releaseDateSplit[0]
                val releaseDateDay = releaseDateSplit[2]
                val releaseDateMonthName = getMonthName(releaseDateMonth.toInt())
                textView.text = "$releaseDateDay $releaseDateMonthName $releaseDateYear"
            } else {
                textView.text = "-"
            }
        }

        private fun getMonthName(toInt: Int): Any {
            return when (toInt) {
                1 -> "January"
                2 -> "February"
                3 -> "March"
                4 -> "April"
                5 -> "May"
                6 -> "June"
                7 -> "July"
                8 -> "August"
                9 -> "September"
                10 -> "October"
                11 -> "November"
                12 -> "December"
                else -> "Unknown"
            }
        }
    }
}
