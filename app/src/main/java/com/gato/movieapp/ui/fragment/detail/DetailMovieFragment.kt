package com.gato.movieapp.ui.fragment.detail

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.gato.movieapp.R
import com.gato.movieapp.adapter.movies.TheCastAdapter
import com.gato.movieapp.data.database.entities.movie.FavoriteMovieEntity
import com.gato.movieapp.databinding.FragmentDetailMovieBinding
import com.gato.movieapp.util.Constants
import com.gato.movieapp.util.NetworkResult
import com.gato.movieapp.viewmodels.DetailViewModel
import com.gato.movieapp.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar


class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailMovieFragmentArgs>()
    private val detailViewModel by viewModels<DetailViewModel>({ requireActivity() })
    private val castAdapter by lazy { TheCastAdapter() }
    private lateinit var mainViewModel: MainViewModel

    private var movieSaved = false
    private var savedMovieId = 0

    private var menuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)

        binding.imageViewMovie.load(Constants.IMAGE_BASE_URL + args.result.posterPath)
        binding.textViewTitle.text = args.result.title
        binding.textViewOverviewDesc.text = args.result.overview
        binding.textViewYearRelease.text = if (args.result.releaseDate.length > 4)
            args.result.releaseDate.substring(0, 4) else "-"
        setupLayoutAbout()
        setupLayoutCast()
        return binding.root
    }

    private fun setupLayoutAbout() {
        detailViewModel.getDetailMovie(args.result.id)
        detailViewModel.detailMovieResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.apply {
                        setShowAnimate(binding.layoutAbout, 1000)
                        setShowAnimate(binding.textViewVoteAvg, 500)
                        setShowAnimate(binding.imageViewStar, 500)
                    }
                    response.data?.let { detail ->
                        binding.detail = detail
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    Log.d("banana", "onCreateView: ${response.message} ${args.result.id}")
                }
                is NetworkResult.Loading -> {
                    binding.layoutAbout.visibility = View.GONE
                    binding.textViewVoteAvg.visibility = View.GONE
                    binding.imageViewStar.visibility = View.GONE
                }
            }
        }
    }

    private fun setupLayoutCast() {
        detailViewModel.getCreditsMovie(args.result.id)
        detailViewModel.creditsMovieResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    setShowAnimate(binding.layoutCast, 1000)
                    response.data?.let { cast ->
                        castAdapter.setData(cast)
                        setupRecyclerView()
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                    Log.d("banana", "onCreateView: ${response.message} ${args.result.id}")
                }
                is NetworkResult.Loading -> {
                    binding.layoutCast.visibility = View.GONE
                }
            }

        }
    }

    private fun setShowAnimate(view: View, duration: Long) {
        view.visibility = View.VISIBLE
        view.alpha = 0f
        view.animate().alpha(1f).setDuration(duration).start()

    }

    private fun setupRecyclerView() {
        binding.rvCast.adapter = castAdapter
        binding.rvCast.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu.findItem(R.id.save_to_favorites_menu)
        checkSavedRecipes(menuItem!!)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
        } else if (item.itemId == R.id.save_to_favorites_menu && !movieSaved) {
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_to_favorites_menu && movieSaved) {
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteMovies.observe(this) { favoritesEntity ->
            try {
                for (savedRecipe in favoritesEntity) {
                    if (savedRecipe.movieResult.id == args.result.id) {
                        changeMenuItemColor(menuItem, R.color.red)
                        savedMovieId = savedRecipe.id
                        movieSaved = true
                    }
                }
            } catch (e: Exception) {
                Log.e("DetailsActivity", e.message.toString())
            }
        }
    }


    private fun saveToFavorites(item: MenuItem) {
        val favoriteMovieEntity = FavoriteMovieEntity(
            0,
            args.result
        )
        mainViewModel.insertFavoriteMovies(favoriteMovieEntity)
        changeMenuItemColor(item, R.color.red)
        showSnackBar("Saved to favorites")
        movieSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoriteMovieEntity = FavoriteMovieEntity(
            savedMovieId,
            args.result
        )
        mainViewModel.deleteFavoriteMovie(favoriteMovieEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from favorites")
        movieSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {
        }.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(requireContext(), color))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        changeMenuItemColor(menuItem!!, R.color.white)
    }
}