package com.gato.movieapp.ui.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gato.movieapp.adapter.movies.FavoriteMoviesAdapter
import com.gato.movieapp.databinding.FragmentFavoriteMovieBinding
import com.gato.movieapp.viewmodels.MainViewModel


class FavoriteMovieFragment : Fragment() {
    private var _binding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val adapter by lazy { FavoriteMoviesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteMovieBinding.inflate(inflater, container, false)

        mainViewModel.readFavoriteMovies.observe(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                binding.recyclerview.adapter = adapter
                binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
                adapter.setData(database)
            }else{
                binding.apply {
                    favMovieImageView.visibility = View.VISIBLE
                    favMovieTextView.visibility = View.VISIBLE
                }
            }

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
