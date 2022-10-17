package com.example.needholiday.ui.quiz

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.needholiday.R
import com.example.needholiday.databinding.FragmentQuizBinding
import com.example.needholiday.di.DaggerAppComponent
import com.example.needholiday.utils.gone
import com.example.needholiday.utils.visible
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class QuizFragment : Fragment(R.layout.fragment_quiz) {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: QuizViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerAppComponent.create().inject(this)

        _binding = FragmentQuizBinding.bind(view)
        viewModel.getQuizzes()

        setUpUi()
        setListeners()
        setClickListeners()
    }

    private fun setUpUi() {
        with(binding) {
            viewModel.dogsViews.addAll(
                listOf(
                    ivDogCenterTop,
                    ivDogLeftBottom,
                    ivDogLeftTop,
                    ivDogRightBottom
                )
            )

            viewModel.nextDog = WeakReference(viewModel.dogsViews[0])

            rgAnswers.setOnCheckedChangeListener { _, checkedElement ->
                setNextButtonAvailability(true)
                when (checkedElement) {
                    answer1.id -> viewModel.totalScores += 1
                    answer2.id -> viewModel.totalScores += 2
                    answer3.id -> viewModel.totalScores += 3
                }
            }
        }
    }

    private fun setListeners() {
        viewModel.quizzes.observe(viewLifecycleOwner) {
            updateViews()
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
            binding.rgAnswers.gone()
            binding.rgAnswers.clearCheck()

            delay(2)

            if (viewModel.isCurrentIndexNotLast.invoke()) {
                viewModel.currentQuizIndex++
                updateViews()
            } else {
                findNavController().navigate(
                    QuizFragmentDirections.actionQuizFragmentToResultFragment(
                        viewModel.totalScores
                    )
                )
            }
        }
    }

    private fun updateViews() {
        showDog()
        showQuiz()
    }

    private fun showQuiz() {
        setNextButtonAvailability(false)
        binding.rgAnswers.visible()
        viewModel.quizzes.value?.get(viewModel.currentQuizIndex).also { quiz ->
            with(binding) {
                tvQuestion.text = getString(quiz!!.question)
                answer1.text = getString(quiz.answers[1]!!)
                answer2.text = getString(quiz.answers[2]!!)
                answer3.text = getString(quiz.answers[3]!!)
            }
        }
    }

    fun setNextButtonAvailability(availability: Boolean) {
        binding.bNext.apply {
            isChecked = availability
            isClickable = availability
            isFocusable = availability
        }
    }

    private fun showDog() {
        viewModel.nextDog?.get()?.visible()
        viewModel.prevDog?.get()?.gone()

        setNewDogData()
    }

    private fun setNewDogData() {
        with(viewModel) {
            prevDog = nextDog
            val currentIndex = dogsViews.indexOf(nextDog?.get())
            nextDog = if (isCurrentIndexNotLast.invoke()) {
                WeakReference(dogsViews[currentIndex + 1])
            } else {
                null
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}