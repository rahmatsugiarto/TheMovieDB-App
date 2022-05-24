package com.gato.movieapp.ui.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gato.movieapp.adapter.tv.FavoriteTvAdapter
import com.gato.movieapp.databinding.FragmentFavoriteTvBinding
import com.gato.movieapp.viewmodels.MainViewModel


class FavoriteTvFragment : Fragment() {
    private var _binding: FragmentFavoriteTvBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private val adapter by lazy { FavoriteTvAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteTvBinding.inflate(inflater, container, false)

        mainViewModel.readFavoriteTv.observe(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                binding.recyclerview.adapter = adapter
                binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
                adapter.setData(database)
            }else{
                binding.apply {
                    favTvImageView.visibility = View.VISIBLE
                    favTvTextView.visibility = View.VISIBLE
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