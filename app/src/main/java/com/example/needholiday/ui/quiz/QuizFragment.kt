package com.example.needholiday.ui.quiz

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.needholiday.App
import com.example.needholiday.R
import com.example.needholiday.databinding.FragmentQuizBinding
import com.example.needholiday.utils.invisible
import com.example.needholiday.utils.visible
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuizFragment : Fragment(R.layout.fragment_quiz) {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: QuizViewModel
    private var currentQuizIndex = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuizBinding.bind(view)
        viewModel = QuizViewModel((requireActivity().application as App).quizRepository)

        viewModel.getQuizzes()
        setListeners()
        setClickListeners()
    }

    private fun setListeners() {
        viewModel.quizzes.observe(viewLifecycleOwner) {
            showQuiz()
        }
    }

    private fun setClickListeners() {
        with(binding) {
            bNext.setOnClickListener {
                nextQuiz()
            }
        }
    }

    private fun nextQuiz() {
        lifecycleScope.launch {
            binding.rgAnswers.apply {
                invisible()
                clearCheck()
            }

            delay(2)

            if (viewModel.quizzes.value?.lastIndex!! > currentQuizIndex) {
                currentQuizIndex++
                showQuiz()
                binding.rgAnswers.visible()
            } else {
                findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
            }
        }
    }

    private fun showQuiz() {
        viewModel.quizzes.value?.get(currentQuizIndex).also { quiz ->
            with(binding) {
                tvQuestion.text = getString(quiz!!.question)
                answer1.text = getString(quiz.answers[1]!!)
                answer2.text = getString(quiz.answers[2]!!)
                answer3.text = getString(quiz.answers[3]!!)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}