package com.example.android.navigation.newQuestion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.database.QuizDatabaseDao
import com.example.android.navigation.database.QuizTable
import kotlinx.coroutines.launch

class NewQuestionViewModel (
        val database: QuizDatabaseDao,
        application: Application) : AndroidViewModel(application) {



    private suspend fun insert(question: QuizTable) {
        database.insert(question)
    }


    fun setQuestion( sentenceQuestion:String, correctanswer:String,
                     wronganswerone:String, wronganswertwo:String, wronganswerthree:String) {
        viewModelScope.launch {
            // Create a new night, which captures the current time,
            // and insert it into the database.
            val newQuestion = QuizTable(questionSentence = sentenceQuestion,correctanswer = correctanswer,
                    wronganswerone = wronganswerone,wronganswertwo = wronganswertwo,wronganswerthree = wronganswerthree)

            insert(newQuestion)


        }
    }




}