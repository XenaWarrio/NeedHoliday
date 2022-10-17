package com.example.needholiday.ui.result

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

    private fun openFile() {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            val s = FileProvider.getUriForFile(
                requireContext(),
                requireContext().applicationContext.packageName + ".provider",
                fileTest!!
            )
            intent.setDataAndType(s, "text/html")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e("FILE_ERROR", "ActivityNotFoundException")
        }
    }
    private fun saveFile() {
        val path = Environment.getExternalStorageDirectory().path
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
        binding.tvResult.text = "${file.path}"
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