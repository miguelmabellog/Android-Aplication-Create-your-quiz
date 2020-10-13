package com.example.android.navigation.showquestions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.navigation.database.QuizDatabaseDao

class ShowViewModel (
        val database: QuizDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    val allQuestions = database.getAllNights()

}