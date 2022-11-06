package com.example.needholiday.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.needholiday.BaseFragment
import com.example.needholiday.constants.LANGUAGE_CODE
import com.example.needholiday.databinding.FragmentMainBinding
import com.example.needholiday.prefs
import com.example.needholiday.utils.verifyStoragePermissions

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}