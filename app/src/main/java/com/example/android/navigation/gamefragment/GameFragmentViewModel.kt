package com.example.android.navigation.gamefragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.database.QuizDatabaseDao
import com.example.android.navigation.database.QuizTable
import kotlinx.coroutines.launch

class GameFragmentViewModel(
        val database: QuizDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    var allRandomQuestions = database.getAllNights()
    private var _nextQuestion=MutableLiveData(0)
    private var _questionSentences=MutableLiveData(" ")


    val nextQuestion: LiveData<Int>
    get()=_nextQuestion

    val questionStences: LiveData<String>
    get()=_questionSentences

    fun nextQuestion(){
        _nextQuestion.value=(_nextQuestion.value ?: 0)+1
    }

    fun shuffleList(): List<QuizTable>? {
        return allRandomQuestions.value?.shuffled()
    }

    fun shuffleOptions(quizTable: QuizTable): List<String>{
        var lista= mutableListOf<String>()
        lista.add(quizTable.correctanswer)
        lista.add(quizTable.wronganswerone)
        lista.add(quizTable.wronganswertwo)
        lista.add(quizTable.wronganswerthree)
        lista.shuffle()

        return lista
    }





}