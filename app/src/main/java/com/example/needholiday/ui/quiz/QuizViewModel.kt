package com.example.needholiday.ui.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.needholiday.domain.Quiz
import com.example.needholiday.repository.QuizRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuizViewModel @Inject constructor(private val repository: QuizRepository) : ViewModel() {
    val quizzes = MutableLiveData<List<Quiz>>()

    init {
        getQuizzes()
    }

    fun getQuizzes() {
        viewModelScope.launch {
            quizzes.postValue(repository.getQuizzes())
        }
    }
}