package com.gato.movieapp.ui.fragment.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.gato.movieapp.databinding.FragmentSearchBottomSheetBinding
import com.gato.movieapp.util.Constants
import com.gato.movieapp.viewmodels.SearchViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class SearchBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentSearchBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchViewModel: SearchViewModel

    private var searchTypeChip = Constants.DEFAULT_SEARCH_TYPE
    private var searchTypeChipId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel = ViewModelProvider(requireActivity())[SearchViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBottomSheetBinding.inflate(inflater, container, false)

        searchViewModel.readSearchType.asLiveData().observe(viewLifecycleOwner) { value ->
            searchTypeChip = value.selectedSearchType
            searchTypeChipId = value.selectedSearchTypeId
            updateChip(value.selectedSearchTypeId, binding.searchTypeChipGroup)

        }

        binding.searchTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedSearchType = chip.text.toString().lowercase()
            searchTypeChip = selectedSearchType
            searchTypeChipId = selectedChipId
        }

        binding.btnApply.setOnClickListener {
            searchViewModel.saveSearchType(searchTypeChip, searchTypeChipId)
            val action = SearchBottomSheetDirections.actionSearchBottomSheetToSearchFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                val targetView = chipGroup.findViewById<Chip>(chipId)
                targetView.isChecked = true
                chipGroup.requestChildFocus(targetView, targetView)
            } catch (e: Exception) {
                Log.d("chip_error", "updateChip: ${e.message}")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}