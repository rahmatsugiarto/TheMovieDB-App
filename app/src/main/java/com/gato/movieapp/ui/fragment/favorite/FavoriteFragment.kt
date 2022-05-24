package com.gato.movieapp.ui.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gato.movieapp.adapter.movies.ViewPagerAdapter
import com.gato.movieapp.databinding.FragmentFavoriteBinding
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        val fragments = ArrayList<Fragment>()
        fragments.add(FavoriteMovieFragment())
        fragments.add(FavoriteTvFragment())

        val titles = ArrayList<String>()
        titles.add("Movie")
        titles.add("TV")

        val adapter =
            ViewPagerAdapter(
                fragments,
                childFragmentManager,
                lifecycle
            )

        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}