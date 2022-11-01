package com.example.needholiday.ui.result

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.needholiday.R
import com.example.needholiday.databinding.FragmentResultBinding
import com.example.needholiday.utils.visible
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class ResultFragment : Fragment(R.layout.fragment_result) {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val navArgs: ResultFragmentArgs by navArgs()
    private val htmlTest = "<html>" +
            "<body>" +
            "<h1>My First Heading</h1>" +
            "<p>My first paragraph.</p>" +
            "</body>" +
            "</html>"

    private val viewModel: ResultViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResultBinding.bind(view)

        setUpUi()
        saveFile()
    }

    private fun setUpUi() {
        binding.bOpenFile.setOnClickListener {
            openFile()
        }

        binding.bShareFile.setOnClickListener {
            shareFile()
        }
    }

    private fun saveFile() {
        val path = requireContext().cacheDir?.path
        val fileName = "Need holiday result : ${
            DateFormat.format(
                "dd_MM_yyyy_hh_mm_ss",
                System.currentTimeMillis()
            )
        }.html"
        viewModel.file = File(path, fileName)

        try {
            val out = FileOutputStream(viewModel.file)
            val data = htmlTest.toByteArray()
            out.write(data)
            out.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun openFile() {
        findNavController().navigate(
            ResultFragmentDirections.actionResultFragmentToOpenFileBottomSheet(
                "file://" + viewModel.file!!.absolutePath
            )
        )
    }

    private fun shareFile() {
        val fileUrl = viewModel.file?.path ?: return

        val uri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().applicationContext.packageName + ".provider",
            viewModel.file!!
        )

        val intent = ShareCompat.IntentBuilder.from(requireActivity())
            .setType("application/${fileUrl.substringAfterLast(".")}")
            .setStream(uri)
            .createChooserIntent()
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(intent)
    }

    private fun showButtons() {
        with(binding) {
            bOpenFile.visible()
            bShareFile.visible()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}