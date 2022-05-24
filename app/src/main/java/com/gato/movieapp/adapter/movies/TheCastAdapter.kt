package com.gato.movieapp.adapter.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gato.movieapp.databinding.ItemRowCastBinding
import com.gato.movieapp.models.Cast
import com.gato.movieapp.models.CreditsResponse
import com.gato.movieapp.util.MyDiffUtil

class TheCastAdapter : RecyclerView.Adapter<TheCastAdapter.MyViewHolder>() {

    private var castList = emptyList<Cast>()

    class MyViewHolder(private val binding: ItemRowCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) {
            binding.cast = cast
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRowCastBinding.inflate(layoutInflater, parent, false)
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
        val cast = castList[position]
        holder.bind(cast)
    }

    override fun getItemCount(): Int = castList.size

    fun setData(newData: CreditsResponse) {
        val diffUtil = MyDiffUtil(castList, newData.cast)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        castList = newData.cast
        diffUtilResult.dispatchUpdatesTo(this)
    }
}