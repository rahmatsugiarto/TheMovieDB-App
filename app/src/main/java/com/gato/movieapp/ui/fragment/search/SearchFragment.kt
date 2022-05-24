package com.gato.movieapp.ui.fragment.search

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gato.movieapp.R
import com.gato.movieapp.adapter.movies.SearchAdapterMovie
import com.gato.movieapp.adapter.tv.SearchAdapterTv
import com.gato.movieapp.databinding.FragmentSearchBinding
import com.gato.movieapp.util.Constants.Companion.TYPE_MOVIE
import com.gato.movieapp.util.Constants.Companion.TYPE_TV
import com.gato.movieapp.viewmodels.MainViewModel
import com.gato.movieapp.viewmodels.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class SearchFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchAdapterMovie by lazy { SearchAdapterMovie() }
    private val searchAdapterTv by lazy { SearchAdapterTv() }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        //make binding.textViewFilter gone when scrolling
        searchViewModel.readSearchType.asLiveData().observe(viewLifecycleOwner) {
            when (it.selectedSearchType) {
                TYPE_MOVIE -> {
                    binding.apply {
                        searchTextView.visibility = View.VISIBLE
                        searchTextView.text = getString(R.string.search_your_movie)
                        searchImageView.visibility = View.VISIBLE
                        searchImageView.setImageResource(R.drawable.ic_movie)
                    }
                }
                TYPE_TV -> {
                    binding.apply {
                        searchTextView.visibility = View.VISIBLE
                        searchTextView.text = getString(R.string.search_your_tv)
                        searchImageView.visibility = View.VISIBLE
                        searchImageView.setImageResource(R.drawable.ic_tv)
                    }
                }
            }
        }

        binding.recyclerViewSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.textViewFilter.visibility = View.GONE
                    binding.textViewFilterValue.visibility = View.GONE
                } else {
                    binding.textViewFilter.visibility = View.VISIBLE
                    binding.textViewFilterValue.visibility = View.VISIBLE
                }
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.menu_searchh)
        val searchView = searchItem.actionView as? SearchView
        searchView?.queryHint = "Search"
        searchView?.setIconifiedByDefault(false)
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_filer -> {
                val action = SearchFragmentDirections.actionSearchFragmentToSearchBottomSheet()
                findNavController().navigate(action)
                Log.d("menu_filter", "onOptionsItemSelected: run")
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchViewModel.readSearchType.asLiveData().observe(viewLifecycleOwner) {
                getSearchMovie(query, it.selectedSearchType)
                binding.textViewFilterValue.text = it.selectedSearchType
                Log.d("type", "onQueryTextSubmit: ${it.selectedSearchType}")
            }

        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false


    private fun getSearchMovie(query: String, type: String) {
        when (type) {
            TYPE_MOVIE -> {
                binding.recyclerViewSearch.adapter = searchAdapterMovie
                binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
                lifecycleScope.launch {
                    searchViewModel.pagerSearchMovie(query).collect { pagingData ->
                        Log.d("flow", "setupShowRecycleView:  $pagingData")
                        searchAdapterMovie.submitData(pagingData)

                        searchAdapterMovie.loadStateFlow
                            .collectLatest {
                                when (it.refresh) {
                                    is LoadState.Loading -> {
                                        showShimmerEffect()
                                        binding.apply {
                                            searchTextView.visibility = View.GONE
                                            searchImageView.visibility = View.GONE
                                        }
                                    }
                                    is LoadState.NotLoading -> {
                                        hideShimmerEffect()
                                        if (searchAdapterTv.itemCount < 0) {
                                            binding.apply {
                                                searchTextView.text = "No Result Found"
                                                searchTextView.visibility = View.VISIBLE
                                                searchImageView.setImageResource(R.drawable.ic_search)
                                                searchImageView.visibility = View.VISIBLE
                                            }
                                        }
                                    }
                                    is LoadState.Error -> {
                                        hideShimmerEffect()
                                        binding.apply {
                                            searchTextView.text = "Error"
                                            searchTextView.visibility = View.VISIBLE
                                            searchImageView.setImageResource(R.drawable.ic_search)
                                            searchImageView.visibility = View.VISIBLE
                                        }
                                    }
                                }
                            }
                    }
                }
            }
            TYPE_TV -> {
                binding.recyclerViewSearch.adapter = searchAdapterTv
                binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
                lifecycleScope.launch {
                    searchViewModel.pagerSearchTv(query).collect { pagingData ->
                        Log.d("flow", "setupShowRecycleView:  $pagingData")
                        searchAdapterTv.submitData(pagingData)

                        searchAdapterTv.loadStateFlow
                            .collectLatest {
                                when (it.refresh) {
                                    is LoadState.Loading -> {
                                        showShimmerEffect()
                                        binding.apply {
                                            searchTextView.visibility = View.GONE
                                            searchImageView.visibility = View.GONE
                                        }
                                    }
                                    is LoadState.NotLoading -> {
                                        hideShimmerEffect()
                                        if (searchAdapterTv.itemCount < 0) {
                                            binding.apply {
                                                searchTextView.text = "No Result Found"
                                                searchTextView.visibility = View.VISIBLE
                                                searchImageView.setImageResource(R.drawable.ic_search)
                                                searchImageView.visibility = View.VISIBLE
                                            }
                                        }
                                    }
                                    is LoadState.Error -> {
                                        hideShimmerEffect()
                                        binding.apply {
                                            searchTextView.text = "Error"
                                            searchTextView.visibility = View.VISIBLE
                                            searchImageView.setImageResource(R.drawable.ic_search)
                                            searchImageView.visibility = View.VISIBLE
                                        }
                                    }
                                }
                            }
                    }
                }
            }
        }
    }

    private fun showShimmerEffect() {
        binding.apply {
            shimmerFrameLayout.startShimmer()
            shimmerFrameLayout.visibility = View.VISIBLE
            recyclerViewSearch.visibility = View.GONE
            textViewFilter.visibility = View.GONE
            textViewFilterValue.visibility = View.GONE
        }

    }

    private fun hideShimmerEffect() {
        binding.apply {
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
            recyclerViewSearch.visibility = View.VISIBLE
            textViewFilter.visibility = View.VISIBLE
            textViewFilterValue.visibility = View.VISIBLE
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}