package com.gato.movieapp.adapter.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gato.movieapp.databinding.ItemRowTvBinding
import com.gato.movieapp.models.TvResponse
import com.gato.movieapp.models.TvResult
import com.gato.movieapp.util.MyDiffUtil

class TrendingTvAdapter : RecyclerView.Adapter<TrendingTvAdapter.MyViewHolder>() {

    private var tvList = emptyList<TvResult>()

    class MyViewHolder(private val binding: ItemRowTvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvResult) {
            binding.result = tv
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRowTvBinding.inflate(layoutInflater, parent, false)
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
        val movie = tvList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = tvList.size

    fun setData(newData: TvResponse) {
        val diffUtil = MyDiffUtil(tvList, newData.tvResults)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        tvList = newData.tvResults
        diffUtilResult.dispatchUpdatesTo(this)
    }
}