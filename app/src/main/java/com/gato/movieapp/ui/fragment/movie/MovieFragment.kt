package com.gato.movieapp.ui.fragment.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gato.movieapp.R
import com.gato.movieapp.adapter.movies.BannerAdapter
import com.gato.movieapp.adapter.movies.PopularMoviesAdapter
import com.gato.movieapp.adapter.movies.TopRatedMoviesAdapter
import com.gato.movieapp.adapter.movies.TrendingMoviesAdapter
import com.gato.movieapp.databinding.FragmentMovieBinding
import com.gato.movieapp.models.MovieResult
import com.gato.movieapp.util.Constants.Companion.API_KEY
import com.gato.movieapp.util.Constants.Companion.POPULAR_MOVIE_TYPE
import com.gato.movieapp.util.Constants.Companion.TOP_RATED_MOVIE_TYPE
import com.gato.movieapp.util.Constants.Companion.TRENDING_MOVIE_TYPE
import com.gato.movieapp.util.Function.Companion.applyQueries
import com.gato.movieapp.util.NetworkListener
import com.gato.movieapp.util.NetworkResult
import com.gato.movieapp.util.observeOnce
import com.gato.movieapp.viewmodels.MainViewModel
import com.gato.movieapp.viewmodels.MovieViewModel
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var movieViewModel: MovieViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private lateinit var networkListener: NetworkListener
    private val trendingAdapter by lazy { TrendingMoviesAdapter() }
    private val popularAdapter by lazy { PopularMoviesAdapter() }
    private val topAdapter by lazy { TopRatedMoviesAdapter() }
    private lateinit var mViewPager: BannerViewPager<MovieResult>

    private var isEmptyDBTrending = false
    private var isEmptyDBPopular = false
    private var isEmptyDBTopRated = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        movieViewModel = ViewModelProvider(requireActivity())[MovieViewModel::class.java]
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        mViewPager = binding.bannerView as BannerViewPager<MovieResult>

        setupRecyclerView()


        lifecycleScope.launchWhenStarted {
            Log.d("launchWhenStarted", "onCreateView: run 1 x")
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    movieViewModel.networkStatus = status
                    Log.d("networkStatus", "onCreateView: networkStatus: $status")

                    movieViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        movieViewModel.readBackOnline.observe(viewLifecycleOwner) {
            movieViewModel.backOnline = it
        }

        binding.seeAllTrending.setOnClickListener {
            val action = MovieFragmentDirections.actionMovieFragmentToSeeAllMovieFragment(
                TRENDING_MOVIE_TYPE
            )
            findNavController().navigate(action)
        }

        binding.seeAllPopular.setOnClickListener {
            val action = MovieFragmentDirections.actionMovieFragmentToSeeAllMovieFragment(
                POPULAR_MOVIE_TYPE
            )
            findNavController().navigate(action)
        }
        binding.seeAllTopRated.setOnClickListener {
            val action = MovieFragmentDirections.actionMovieFragmentToSeeAllMovieFragment(
                TOP_RATED_MOVIE_TYPE
            )
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun readDatabase() {
        Log.d("readDatabase", "readDatabase: run")
        lifecycleScope.launch {
            if (!mainViewModel.hasInternetConnection()) {
                mainViewModel.readTrendingMovies.observeOnce(viewLifecycleOwner) { database ->
                    Log.d("readTrendingMovies", "readDatabase: run 1")
                    if (database.isNotEmpty()) {
                        trendingAdapter.setData(database.first().moviesResponse)
                        hideShimmerEffect()
                    } else {
                        hideAndShowTextView(binding.textViewTrending, binding.seeAllTrending, false)
                        isEmptyDBTrending = true
                        handleErrorDatabase()
                    }
                }
                mainViewModel.readPopularMovies.observeOnce(viewLifecycleOwner) { database ->
                    Log.d("readTrendingMovies", "readDatabase: run 1")
                    if (database.isNotEmpty()) {
                        popularAdapter.setData(database.first().moviesResponse)
                        hideShimmerEffect()
                    } else {
                        hideAndShowTextView(binding.textViewTrending, binding.seeAllTrending, false)
                        isEmptyDBTrending = true
                        handleErrorDatabase()
                    }
                }
                mainViewModel.readTopRatedMovies.observeOnce(viewLifecycleOwner) { database ->
                    Log.d("readTrendingMovies", "readDatabase: run 1")
                    if (database.isNotEmpty()) {
                        topAdapter.setData(database.first().moviesResponse)
                        hideShimmerEffect()
                    } else {
                        hideAndShowTextView(binding.textViewTrending, binding.seeAllTrending, false)
                        isEmptyDBTrending = true
                        handleErrorDatabase()
                    }
                }
                mainViewModel.readUpComingMovies.observeOnce(viewLifecycleOwner) { database ->
                    Log.d("readTrendingMovies", "readDatabase: run 1")
                    if (database.isNotEmpty()) {
                        setupViewPager(database.first().upComingResponse.movieResults)
                        hideShimmerEffect()
                    }
                }
            } else {
                requestApiData()
                hideAndShowTextView(binding.textViewTrending, binding.seeAllTrending, true)
                hideAndShowTextView(binding.textViewPopular, binding.seeAllPopular, true)
                hideAndShowTextView(binding.textViewTopRated, binding.seeAllTopRated, true)
            }

        }
    }


    private fun hideAndShowTextView(textView1: TextView, textView2: TextView, show: Boolean) {
        textView1.isVisible = show
        textView2.isVisible = show
    }

    private fun handleErrorDatabase() {
        if (isEmptyDBTrending && isEmptyDBPopular && isEmptyDBTopRated) {
            binding.errorImageView.visibility = View.VISIBLE
            binding.errorTextView.visibility = View.VISIBLE
            binding.svMovies.visibility = View.GONE
            binding.shimmerFrameLayout.stopShimmer()
            binding.shimmerFrameLayout.visibility = View.GONE
        }
    }


    private fun requestApiData() {
        Log.d("tess", "requestApiData: Running")

        mainViewModel.getTrendingMovies(API_KEY)
        mainViewModel.getPopularMovies(applyQueries())
        mainViewModel.getTopRatedMovies(applyQueries())
        mainViewModel.getUpComingMovies(applyQueries())

        mainViewModel.trendingMoviesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { trendingAdapter.setData(it) }

                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
//                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    Log.d("trendingMoviesResponse", "requestApiData: ${response.message}")
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }

        mainViewModel.popularMoviesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { popularAdapter.setData(it) }

                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
//                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    Log.d("popularMoviesResponse", "requestApiData: ${response.message}")
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
        mainViewModel.topRatedMoviesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { topAdapter.setData(it) }

                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    Log.d("topRatedMoviesResponse", "requestApiData: ${response.message}")
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
        mainViewModel.upComingMoviesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let {
                        setupViewPager(it.movieResults)
                        Log.d("benner", "requestApiData: $it")
                    }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
//                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readTrendingMovies.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    trendingAdapter.setData(database.first().moviesResponse)
                }
            }
            mainViewModel.readPopularMovies.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    popularAdapter.setData(database.first().moviesResponse)
                }
            }
            mainViewModel.readTopRatedMovies.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    topAdapter.setData(database.first().moviesResponse)
                }
            }
            mainViewModel.readUpComingMovies.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    setupViewPager(database.first().upComingResponse.movieResults)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvTrending.adapter = trendingAdapter
        binding.rvTrending.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvPopular.adapter = popularAdapter
        binding.rvPopular.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvTopRated.adapter = topAdapter
        binding.rvTopRated.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        showShimmerEffect()

    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.svMovies.visibility = View.GONE
        binding.errorTextView.visibility = View.GONE
        binding.errorImageView.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.svMovies.visibility = View.VISIBLE
    }

    private fun setupViewPager(data: List<MovieResult>) {
        val list = data.take(5)
        mViewPager.apply {
            adapter = BannerAdapter()
            setLifecycleRegistry(lifecycle)
        }
            .setLifecycleRegistry(lifecycle)
            .setIndicatorStyle(IndicatorStyle.CIRCLE)
            .setIndicatorSlideMode(3)
            .setIndicatorSliderGap(resources.getDimensionPixelOffset(R.dimen.dp_6))
            .setIndicatorSliderRadius(
                resources.getDimensionPixelOffset(R.dimen.dp_4),
                resources.getDimensionPixelOffset(R.dimen.dp_5)
            )
            .setPageMargin(15)
            .setScrollDuration(800)
            .setIndicatorSlideMode(IndicatorSlideMode.WORM)
            .setRevealWidth(
                resources.getDimensionPixelOffset(R.dimen.dp_10),
                resources.getDimensionPixelOffset(R.dimen.dp_10)
            )
            .setPageStyle(1 shl 3)
            .setIndicatorSliderColor(
                ContextCompat.getColor(requireContext(), R.color.white_trans),
                ContextCompat.getColor(requireContext(), R.color.light_blue)
            )
            .create(list)
    }

    override fun onPause() {
        mViewPager.stopLoop()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mViewPager.startLoop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mViewPager.stopLoop()
    }

}

