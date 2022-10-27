package com.example.needholiday.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.needholiday.databinding.BottomSheetOpenFileBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class OpenFileBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetOpenFileBinding? = null
    private val binding get() = _binding!!
    private val args : OpenFileBottomSheetArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetOpenFileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //doesn't work

        with(binding){
            webView.settings.allowFileAccess = true
            webView.loadUrl(args.fileUri)
        }
    }
}