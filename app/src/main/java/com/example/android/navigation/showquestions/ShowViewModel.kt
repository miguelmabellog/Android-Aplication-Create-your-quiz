package com.example.android.navigation.showquestions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.database.QuizDatabaseDao
import kotlinx.coroutines.launch

class ShowViewModel (
        val database: QuizDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    val allQuestions = database.getAllQuestions()


    fun deleteId(key: Long) {
        viewModelScope.launch {
            database.deleteId(key)
        }
    }

    private suspend fun clear() {
        database.clear()
    }
    fun onClear() {

        viewModelScope.launch {
            clear()
        }


    }

}