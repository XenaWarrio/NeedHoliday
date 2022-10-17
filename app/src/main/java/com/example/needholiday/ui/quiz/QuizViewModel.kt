package com.example.needholiday.ui.quiz

import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.needholiday.domain.Quiz
import com.example.needholiday.repository.QuizRepository
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class QuizViewModel @Inject constructor(private val repository: QuizRepository) : ViewModel() {
    val quizzes = MutableLiveData<List<Quiz>>()
    var dogsViews = mutableListOf<AppCompatImageView>()

    var totalScores = 0
    var currentQuizIndex = 0

    var prevDog: WeakReference<AppCompatImageView>? = null
    var nextDog: WeakReference<AppCompatImageView>? = null

    val isCurrentIndexNotLast: () -> Boolean = { quizzes.value?.lastIndex!! > currentQuizIndex}

    init {
        getQuizzes()
    }

    fun getQuizzes() {
        viewModelScope.launch {
            quizzes.postValue(repository.getQuizzes())
        }
    }

    override fun onCleared() {
        super.onCleared()
        prevDog = null
        nextDog = null
    }
}