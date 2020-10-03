package com.example.android.navigation.newQuestion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.android.navigation.database.QuizDatabaseDao

class NewQuestionViewModel (
        val database: QuizDatabaseDao,
        application: Application) : AndroidViewModel(application) {

}