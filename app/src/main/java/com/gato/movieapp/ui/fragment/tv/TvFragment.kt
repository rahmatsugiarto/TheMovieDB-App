package com.gato.movieapp.ui.fragment.tv

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gato.movieapp.adapter.tv.PopularTvAdapter
import com.gato.movieapp.adapter.tv.TopRatedTvAdapter
import com.gato.movieapp.adapter.tv.TrendingTvAdapter
import com.gato.movieapp.databinding.FragmentTvBinding
import com.gato.movieapp.util.Constants
import com.gato.movieapp.util.Function.Companion.applyQueries
import com.gato.movieapp.util.NetworkListener
import com.gato.movieapp.util.NetworkResult
import com.gato.movieapp.util.observeOnce
import com.gato.movieapp.viewmodels.MainViewModel
import com.gato.movieapp.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvFragment : Fragment() {

    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var movieViewModel: MovieViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private lateinit var networkListener: NetworkListener
    private val trendingAdapter by lazy { TrendingTvAdapter() }
    private val popularAdapter by lazy { PopularTvAdapter() }
    private val topAdapter by lazy { TopRatedTvAdapter() }

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
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

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
            val action = TvFragmentDirections.actionTvFragmentToMovieDescFragment(
                Constants.TRENDING_TV_TYPE
            )
            findNavController().navigate(action)
        }

        binding.seeAllPopular.setOnClickListener {
            val action = TvFragmentDirections.actionTvFragmentToMovieDescFragment(
                Constants.POPULAR_TV_TYPE
            )
            findNavController().navigate(action)
        }
        binding.seeAllTopRated.setOnClickListener {
            val action = TvFragmentDirections.actionTvFragmentToMovieDescFragment(
                Constants.TOP_RATED_TV_TYPE
            )
            findNavController().navigate(action)
        }
        return binding.root
    }
    private fun readDatabase() {
        Log.d("readDatabase", "readDatabase: run")
        lifecycleScope.launch {
            if (!mainViewModel.hasInternetConnection()) {
                mainViewModel.readTrendingTv.observeOnce(viewLifecycleOwner) { database ->
                    Log.d("readTrendingTv", "readDatabase: run 1")
                    if (database.isNotEmpty()) {
                        trendingAdapter.setData(database.first().tvResponse)
                        hideShimmerEffect()
                    } else {
                        hideAndShowTextView(binding.textViewTrending, binding.seeAllTrending, false)
                        isEmptyDBTrending = true
                        handleErrorDatabase()
                    }
                }
                mainViewModel.readPopularTv.observeOnce(viewLifecycleOwner) { database ->
                    Log.d("readTrendingTv", "readDatabase: run 1")
                    if (database.isNotEmpty()) {
                        popularAdapter.setData(database.first().tvResponse)
                        hideShimmerEffect()
                    } else {
                        hideAndShowTextView(binding.textViewTrending, binding.seeAllTrending, false)
                        isEmptyDBTrending = true
                        handleErrorDatabase()
                    }
                }
                mainViewModel.readTopRatedTv.observeOnce(viewLifecycleOwner) { database ->
                    Log.d("readTrendingTv", "readDatabase: run 1")
                    if (database.isNotEmpty()) {
                        topAdapter.setData(database.first().tvResponse)
                        hideShimmerEffect()
                    } else {
                        hideAndShowTextView(binding.textViewTrending, binding.seeAllTrending, false)
                        isEmptyDBTrending = true
                        handleErrorDatabase()
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
            binding.svTv.visibility = View.GONE
            binding.shimmerFrameLayout.stopShimmer()
            binding.shimmerFrameLayout.visibility = View.GONE
        }
    }


    private fun requestApiData() {
        Log.d("tess", "requestApiData: Running")

        mainViewModel.getTrendingTv(Constants.API_KEY)
        mainViewModel.getPopularTv(applyQueries())
        mainViewModel.getTopRatedTv(applyQueries())

        mainViewModel.trendingTvResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { trendingAdapter.setData(it) }

                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
//                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    Log.d("trendingTvResponse", "requestApiData: ${response.message}")
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }

        mainViewModel.popularTvResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { popularAdapter.setData(it) }

                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
//                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    Log.d("popularTvResponse", "requestApiData: ${response.message}")
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
        mainViewModel.topRatedTvResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { topAdapter.setData(it) }

                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
//                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    Log.d("topRatedTvResponse", "requestApiData: ${response.message}")
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readTrendingTv.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    trendingAdapter.setData(database.first().tvResponse)
                }
            }
            mainViewModel.readPopularTv.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    popularAdapter.setData(database.first().tvResponse)
                }
            }
            mainViewModel.readTopRatedTv.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    topAdapter.setData(database.first().tvResponse)
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
        binding.svTv.visibility = View.GONE
        binding.errorTextView.visibility = View.GONE
        binding.errorImageView.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.svTv.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}