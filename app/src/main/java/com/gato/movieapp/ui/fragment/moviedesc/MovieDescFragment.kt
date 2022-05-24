package com.gato.movieapp.ui.fragment.moviedesc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.gato.movieapp.adapter.movies.PopularSeeAllMovieAdapter
import com.gato.movieapp.adapter.movies.TopRatedSeeAllMovieAdapter
import com.gato.movieapp.adapter.movies.TrendingSeeAllMovieAdapter
import com.gato.movieapp.adapter.tv.PopularSeeAllTvAdapter
import com.gato.movieapp.adapter.tv.TopRatedSeeAllTvAdapter
import com.gato.movieapp.adapter.tv.TrendingSeeAllTvAdapter
import com.gato.movieapp.databinding.FragmentMovieDescBinding
import com.gato.movieapp.util.Constants.Companion.POPULAR_MOVIE_TYPE
import com.gato.movieapp.util.Constants.Companion.POPULAR_TV_TYPE
import com.gato.movieapp.util.Constants.Companion.TOP_RATED_MOVIE_TYPE
import com.gato.movieapp.util.Constants.Companion.TOP_RATED_TV_TYPE
import com.gato.movieapp.util.Constants.Companion.TRENDING_MOVIE_TYPE
import com.gato.movieapp.util.Constants.Companion.TRENDING_TV_TYPE
import com.gato.movieapp.viewmodels.MovieDescViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDescFragment : Fragment() {

    private var _binding: FragmentMovieDescBinding? = null
    private val binding get() = _binding!!

    private val movieDescViewModel by viewModels<MovieDescViewModel>()
    private val trendingMoviePagingAdapter by lazy { TrendingSeeAllMovieAdapter() }
    private val popularMoviePagingAdapter by lazy { PopularSeeAllMovieAdapter() }
    private val topRatedMoviePagingAdapter by lazy { TopRatedSeeAllMovieAdapter() }
    private val trendingTvPagingAdapter by lazy { TrendingSeeAllTvAdapter() }
    private val popularTvPagingAdapter by lazy { PopularSeeAllTvAdapter() }
    private val topRatedTvPagingAdapter by lazy { TopRatedSeeAllTvAdapter() }
    private val args by navArgs<MovieDescFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDescBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.title = args.type

        when (args.type) {
            TRENDING_MOVIE_TYPE -> {
                binding.recyclerview.adapter = trendingMoviePagingAdapter
                binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
                binding.recyclerview.setHasFixedSize(true)
                lifecycleScope.launch {
                    movieDescViewModel.pagerTrendingMovie.collectLatest { pagingData ->
                        trendingMoviePagingAdapter.submitData(pagingData)
                    }

                }
                viewLifecycleOwner.lifecycleScope.launch {
                    trendingMoviePagingAdapter.loadStateFlow
                        .collectLatest {
                            when (it.refresh) {
                                is LoadState.Loading -> {
                                    showShimmerEffect()
                                }
                                is LoadState.NotLoading -> {
                                    hideShimmerEffect()
                                }
                                is LoadState.Error -> {
                                    hideShimmerEffect()
                                }
                            }
                        }
                }
            }
            POPULAR_MOVIE_TYPE -> {
                binding.recyclerview.adapter = popularMoviePagingAdapter
                binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
                lifecycleScope.launch {
                    movieDescViewModel.pagerPopularMovie.collectLatest { pagingData ->
                        popularMoviePagingAdapter.submitData(pagingData)
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    popularMoviePagingAdapter.loadStateFlow
                        .collectLatest {
                            when (it.refresh) {
                                is LoadState.Loading -> {
                                    showShimmerEffect()
                                }
                                is LoadState.NotLoading -> {
                                    hideShimmerEffect()
                                }
                                is LoadState.Error -> {
                                    hideShimmerEffect()
                                }
                            }
                        }
                }

            }
            TOP_RATED_MOVIE_TYPE -> {
                binding.recyclerview.adapter = topRatedMoviePagingAdapter
                binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
                lifecycleScope.launch {
                    movieDescViewModel.pagerTopRatedMovie.collectLatest { pagingData ->
                        topRatedMoviePagingAdapter.submitData(pagingData)
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    topRatedMoviePagingAdapter.loadStateFlow
                        .collectLatest {
                            when (it.refresh) {
                                is LoadState.Loading -> {
                                    showShimmerEffect()
                                }
                                is LoadState.NotLoading -> {
                                    hideShimmerEffect()
                                }
                                is LoadState.Error -> {
                                    hideShimmerEffect()
                                }
                            }
                        }
                }
            }
            TRENDING_TV_TYPE -> {
                binding.recyclerview.adapter = trendingTvPagingAdapter
                binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
                lifecycleScope.launch {
                    movieDescViewModel.pagerTrendingTv.collectLatest { pagingData ->
                        trendingTvPagingAdapter.submitData(pagingData)
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    trendingTvPagingAdapter.loadStateFlow
                        .collectLatest {
                            when (it.refresh) {
                                is LoadState.Loading -> {
                                    showShimmerEffect()
                                }
                                is LoadState.NotLoading -> {
                                    hideShimmerEffect()
                                }
                                is LoadState.Error -> {
                                    hideShimmerEffect()
                                }
                            }
                        }
                }
            }
            POPULAR_TV_TYPE -> {
                binding.recyclerview.adapter = popularTvPagingAdapter
                binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
                lifecycleScope.launch {
                    movieDescViewModel.pagerPopularTv.collectLatest { pagingData ->
                        popularTvPagingAdapter.submitData(pagingData)
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    popularTvPagingAdapter.loadStateFlow
                        .collectLatest {
                            when (it.refresh) {
                                is LoadState.Loading -> {
                                    showShimmerEffect()
                                }
                                is LoadState.NotLoading -> {
                                    hideShimmerEffect()
                                }
                                is LoadState.Error -> {
                                    hideShimmerEffect()
                                }
                            }
                        }
                }
            }
            TOP_RATED_TV_TYPE -> {
                binding.recyclerview.adapter = topRatedTvPagingAdapter
                binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
                lifecycleScope.launch {
                    movieDescViewModel.pagerTopRatedTv.collectLatest { pagingData ->
                        topRatedTvPagingAdapter.submitData(pagingData)
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    topRatedTvPagingAdapter.loadStateFlow
                        .collectLatest {
                            when (it.refresh) {
                                is LoadState.Loading -> {
                                    showShimmerEffect()
                                }
                                is LoadState.NotLoading -> {
                                    hideShimmerEffect()
                                }
                                is LoadState.Error -> {
                                    hideShimmerEffect()
                                }
                            }
                        }
                }
            }
        }

        return binding.root
    }

    private fun showShimmerEffect() {
        binding.apply {
            shimmerFrameLayout.startShimmer()
            shimmerFrameLayout.visibility = View.VISIBLE
            recyclerview.visibility = View.GONE
        }

    }

    private fun hideShimmerEffect() {
        binding.apply {
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}