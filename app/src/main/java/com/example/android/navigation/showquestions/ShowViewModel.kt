package com.example.android.navigation.showquestions

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.database.QuizDatabaseDao
import kotlinx.coroutines.launch

class ShowViewModel (
        val database: QuizDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    val allQuestions = database.getAllNights()

    private suspend fun clear() {

    }
    fun deleteId(key: Long) {
        viewModelScope.launch {
            database.deleteId(key)
        }
    }

}