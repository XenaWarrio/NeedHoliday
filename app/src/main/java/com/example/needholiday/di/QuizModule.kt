package com.example.needholiday.di

import com.example.needholiday.repository.QuizRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class QuizModule {
    @Provides
    @Singleton
    fun provideQuizRepository() = QuizRepository
}