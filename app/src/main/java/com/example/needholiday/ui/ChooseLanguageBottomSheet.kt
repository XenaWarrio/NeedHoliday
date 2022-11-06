package com.example.needholiday.ui

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.needholiday.BaseBottomSheet
import com.example.needholiday.constants.LANGUAGE_CODE
import com.example.needholiday.databinding.BottomSheetChooseLanguageBinding

class ChooseLanguageBottomSheet :
    BaseBottomSheet<BottomSheetChooseLanguageBinding>(BottomSheetChooseLanguageBinding::inflate) {

    private val args: ChooseLanguageBottomSheetArgs by navArgs()
    private val check: (radioButton: RadioButton) -> Unit = { rb -> rb.isChecked = true }
    private val close = { findNavController().popBackStack() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        with(binding) {
            setCheckedCurrentLanguage(args.selectedLanguage)
            rgLanguages.setOnCheckedChangeListener { _, id ->
                var language = ""
                when (id) {
                    en.id -> language = "en"
                    de.id -> language = "de"
                    ru.id -> language = "ru"
                    uk.id -> language = "uk"
                    es.id -> language = "es"
                }
                setFragmentResult(LANGUAGE_CODE, bundleOf(LANGUAGE_CODE to language))
                close()
            }
            btnCancel.setOnClickListener {
                close()
            }
        }
    }

    private fun setCheckedCurrentLanguage(selectedLanguage: String) {
        with(binding) {
            when (selectedLanguage) {
                "en" -> check(en)
                "de" -> check(de)
                "ru" -> check(ru)
                "uk" -> check(uk)
                "es" -> check(es)
                else -> {}
            }
        }
    }
}