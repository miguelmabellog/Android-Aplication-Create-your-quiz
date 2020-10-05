package com.example.android.navigation.newQuestion

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.database.QuizDatabaseDao
import com.example.android.navigation.database.QuizTable
import kotlinx.coroutines.launch

class NewQuestionViewModel (
        val database: QuizDatabaseDao,
        application: Application) : AndroidViewModel(application) {



    private suspend fun insert(question: QuizTable) {
        Log.i("inseratar datos", database.getAllNights().value?.size.toString())
        database.insert(question)
        Log.i("inseratar datos", database.getAllNights().value?.size.toString())
    }




    fun setQuestion( sentenceQuestion:String, correctanswer:String,
                     wronganswerone:String, wronganswertwo:String, wronganswerthree:String) {
        viewModelScope.launch {
            val newQuestion = QuizTable(questionSentence = sentenceQuestion,correctanswer = correctanswer,
                    wronganswerone = wronganswerone,wronganswertwo = wronganswertwo,wronganswerthree = wronganswerthree)
            insert(newQuestion)

        }
    }

    val navigateToTitle: LiveData<QuizTable>
    get()=_navigateToTitle


    private val _navigateToTitle = MutableLiveData<QuizTable>()



}