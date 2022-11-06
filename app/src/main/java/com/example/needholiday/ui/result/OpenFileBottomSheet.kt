package com.example.needholiday.ui.result

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.needholiday.BaseBottomSheet
import com.example.needholiday.databinding.BottomSheetOpenFileBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class OpenFileBottomSheet :
    BaseBottomSheet<BottomSheetOpenFileBinding>(BottomSheetOpenFileBinding::inflate) {

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
                    Log.e("OpenFileBottomSheet", "error: $error")
                }
            }
            loadUrl(args.fileUri)
        }
    }
}