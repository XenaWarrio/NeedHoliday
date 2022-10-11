package com.example.needholiday.di

import com.example.needholiday.ui.quiz.QuizFragment
import dagger.Component

@Component(modules = [QuizModule::class])
interface AppComponent {
    fun inject(quizFragment: QuizFragment)
}