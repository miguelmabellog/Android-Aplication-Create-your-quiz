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

    private val nights = database.getAllNights()

    private var tonight = MutableLiveData<QuizTable?>()

    fun allnights(): LiveData<List<QuizTable>> {

        return nights
    }


    init {
        initializeTonight()

    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getTonightFromDatabase()
        }
    }



    private suspend fun getTonightFromDatabase(): QuizTable? {
        var night = database.getTonight()

        return night
    }



    fun setQuestion(questionSentence:String, correctAnswer:String, wrongone:String, wrongtwo:String, wrongthree:String) {

        viewModelScope.launch {
            val newQuestion = QuizTable(questionSentence = questionSentence, correctanswer = correctAnswer,
            wronganswerone = wrongone, wronganswertwo = wrongtwo,wronganswerthree = wrongthree)
            insert(newQuestion)
            tonight.value = getTonightFromDatabase()

        }
        Log.i("inseratar clear", nights.value?.size.toString())

    }
    private suspend fun insert(question: QuizTable) {

        database.insert(question)

    }

    private suspend fun clear() {
        database.clear()
    }
    fun onClear() {

        viewModelScope.launch {
            clear()
        }
        Log.i("inseratar clear", nights.value?.size.toString())

    }





}