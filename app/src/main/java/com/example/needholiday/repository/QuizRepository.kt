package com.example.needholiday.repository

import com.example.needholiday.data.GetQuizzes
import com.example.needholiday.domain.Quiz

object QuizRepository {
    fun getQuizzes(): List<Quiz> {
        return GetQuizzes.getQuizzes()
    }
}