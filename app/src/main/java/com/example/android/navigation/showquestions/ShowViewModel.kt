package com.example.android.navigation.showquestions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.toLiveData
import com.example.android.navigation.database.QuizDatabaseDao
import kotlinx.coroutines.launch

class ShowViewModel (
        val database: QuizDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    val allQuestions = database.getAllQuestions().toLiveData(pageSize = 10)


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