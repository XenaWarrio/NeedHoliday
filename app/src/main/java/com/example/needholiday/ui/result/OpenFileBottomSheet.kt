package com.example.needholiday.ui.result

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.needholiday.databinding.BottomSheetOpenFileBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class OpenFileBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetOpenFileBinding? = null
    private val binding get() = _binding!!
    private val args: OpenFileBottomSheetArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?) = getStyledDialog(savedInstanceState)

    private fun getStyledDialog(savedInstanceState: Bundle?): BottomSheetDialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomDialog = it as BottomSheetDialog
            val bottomSheet =
                bottomDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            bottomSheet?.let {
                BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

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

        openFile()
        setUpUi()
    }

    private fun setUpUi() {
        with(binding) {
            btnCancel.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun openFile() {
        with(binding.webView) {
            settings.allowFileAccess = true
            webViewClient = object : WebViewClient() {
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    Log.e("OpenFileBottomSheet", "error: ${error?.description ?: ""}")
                }
            }
            loadUrl(args.fileUri)
        }
    }
}