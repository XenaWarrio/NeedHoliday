package com.example.needholiday.ui.result

import android.content.Intent
import android.os.Bundle
import android.os.Environment
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
    var fileTest: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResultBinding.bind(view)
        binding.tvResult.text = "${navArgs.totalScores}"
        saveFile()
        binding.bOpenFile.setOnClickListener {
            openFile()
        }
    }

    private fun  openFile() {
        findNavController().navigate(ResultFragmentDirections.actionResultFragmentToOpenFileBottomSheet("file://"+fileTest!!.absolutePath))
    }

    private fun shareFile() {
        val fileUrl = fileTest!!.path

        val uri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().applicationContext.packageName + ".provider",
            fileTest!!
        )

        val intent = ShareCompat.IntentBuilder.from(requireActivity())
            .setType("application/${fileUrl.substringAfterLast(".")}")
            .setStream(uri)
            .createChooserIntent()
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        startActivity(intent)
    }
    private fun saveFile() {
        val path = requireContext().cacheDir?.path
        var fileName = "Need holiday result : ${
            DateFormat.format(
                "dd_MM_yyyy_hh_mm_ss",
                System.currentTimeMillis()
            )
        }"
        fileName = "$fileName.html"
        val file = File(path, fileName)
        fileTest = file
        viewModel.filePath = file.path
        binding.tvResult.text = file.path
        binding.bOpenFile.visible()

        try {
            val out = FileOutputStream(file)
            val data = htmlTest.toByteArray()
            out.write(data)
            out.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}