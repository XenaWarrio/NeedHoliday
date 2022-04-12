package com.example.needholiday.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.needholiday.constants.LANGUAGE_CODE
import com.example.needholiday.databinding.FragmentMainBinding
import com.example.needholiday.prefs

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpListeners()
    }

    private fun setUpListeners() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}