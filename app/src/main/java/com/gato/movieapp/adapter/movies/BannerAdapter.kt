package com.gato.movieapp.adapter.movies

import android.util.Log
import coil.load
import com.gato.movieapp.R
import com.gato.movieapp.databinding.ItemRowBannerBinding
import com.gato.movieapp.models.MovieResult
import com.gato.movieapp.util.Constants
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

class BannerAdapter:BaseBannerAdapter<MovieResult>() {
    override fun bindData(
        holder: BaseViewHolder<MovieResult>?,
        data: MovieResult?,
        position: Int,
        pageSize: Int
    ) {
        val binding = ItemRowBannerBinding.bind(holder?.itemView!!)
        Log.d("benner", "bindData:Runnn ")
        binding.bannerImage.load(Constants.IMAGE_BASE_URL + data!!.backdropPath){
            crossfade(true)
            error(R.drawable.ic_error_placeholder)
        }
        binding.movieDescribe.text = data.title

    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_row_banner
    }
}


//class SimpleAdapter : BaseBannerAdapter<UpComingResponse>() {
//
//    override fun bindData(holder: BaseViewHolder<CustomBean>, data: CustomBean?, position: Int, pageSize: Int) {
//        val imageStart: ImageView = holder.findViewById(R.id.iv_logo)
//        holder.setImageResource(R.id.banner_image, data!!.imageRes)
//    }
//
//    override fun getLayoutId(viewType: Int): Int {
//        return R.layout.item_custom_view;
//    }
//}

//public class SimpleAdapter extends BaseBannerAdapter<UpComingResponse> {
//
//    @Override
//    protected void bindData(BaseViewHolder<UpComingResponse> holder, UpComingResponse data, int position, int pageSize) {
//        //示例使用ViewBinding
//        ItemRowBannerBinding viewBinding = ItemRowBannerBinding.bind(holder.itemView);
//        viewBinding.bannerImage.setImageResource(data.getMovieResults().get(position).getPosterPath());
//    }
//
//    @Override
//    public int getLayoutId(int viewType) {
//        return R.layout.item_row_banner;
//    }
//}