package com.example.needholiday.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.needholiday.R
import com.example.needholiday.constants.LANGUAGE_CODE
import com.example.needholiday.databinding.FragmentMainBinding
import com.example.needholiday.prefs
import com.example.needholiday.utils.verifyStoragePermissions

class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        setUpViews()
        setListeners()
        setClickListeners()

        requireActivity().verifyStoragePermissions()
    }

    private fun setListeners() {
        setFragmentResultListener(LANGUAGE_CODE) { _, bundle ->
            bundle.getString(LANGUAGE_CODE)?.let {
                binding.btnLanguageChange.text = it
                prefs.language = it
                requireActivity().recreate()
            }
        }
    }

    private fun setUpViews() {
        binding.btnLanguageChange.apply {
            text = prefs.language
            setOnClickListener {
                findNavController().navigate(
                    MainFragmentDirections.actionToChooseLanguageBottomSheet(
                        prefs.language
                    )
                )
            }
        }
    }

    private fun setClickListeners() {
        with(binding) {
            bPassTest.setOnClickListener {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToQuizFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}