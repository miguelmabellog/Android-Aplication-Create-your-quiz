package com.example.android.navigation.gamefragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.database.QuizDatabaseDao
import com.example.android.navigation.database.QuizTable
import kotlinx.coroutines.launch

class GameFragmentViewModel(
        val database: QuizDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    private val nights = database.getAllNights()
    var questionSentence:String=""

    init {
        getQuestion()
    }



    fun getQuestion() {
        Log.i("entra al init", "antes del scope")
        viewModelScope.launch {

            val tonight = nights.value?.get(0)?.questionId?.let {
                database.get(it)

            } ?: return@launch
            questionSentence=tonight.questionId.toString()
            /*nights[0]?.let {
                val tonight = database.get(nights[0].questionId) ?: return@launch
                questionSentence=tonight.questionSentence
            }*/

        }
        Log.i("all the nights", nights.value?.get(0).toString())
    }






}