package com.example.needholiday.data

import com.example.needholiday.R
import com.example.needholiday.domain.Quiz

object GetQuizzes {
     fun getQuizzes(): List<Quiz> {
        val quizzes = mutableListOf<Quiz>()
        quizzes.add(
            Quiz(
                (R.string.question_1),
                hashMapOf(
                    1 to R.string.answer_1_1,
                    2 to R.string.answer_1_2,
                    3 to R.string.answer_1_3
                )
            )
        )
        quizzes.add(
            Quiz(
                (R.string.question_2),
                hashMapOf(
                    1 to R.string.answer_2_1,
                    2 to R.string.answer_2_2,
                    3 to R.string.answer_2_3
                )
            )
        )
        quizzes.add(
            Quiz(
                (R.string.question_3),
                hashMapOf(
                    1 to R.string.answer_3_1,
                    2 to R.string.answer_3_2,
                    3 to R.string.answer_3_3
                )
            )
        )
        return quizzes
    }
}