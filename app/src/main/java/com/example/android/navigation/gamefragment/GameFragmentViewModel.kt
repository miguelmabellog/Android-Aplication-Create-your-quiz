package com.example.android.navigation.gamefragment

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.android.navigation.database.QuizDatabaseDao
import com.example.android.navigation.database.QuizTable

class GameFragmentViewModel(
        val database: QuizDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    var allRandomQuestions = database.getAllQuestions()
    private lateinit var shuffledQuestions:List<QuizTable>
    private var _nextQuestion=MutableLiveData(0)
    private var _questionSentences=MutableLiveData(" ")


    val questionSentence:LiveData<String>
    get() = _questionsSentence
    private var _questionsSentence=MutableLiveData<String>()

    val firstAsnwer:LiveData<String>
        get() = _firstAsnwer
    private var _firstAsnwer=MutableLiveData<String>()

    val secondAnswer:LiveData<String>
        get() = _secondAnswer
    private var _secondAnswer=MutableLiveData<String>()

    val tirthAnswer:LiveData<String>
        get() = _tirthAnswer
    private var _tirthAnswer=MutableLiveData<String>()

    val forthAnswer:LiveData<String>
        get() = _forthAnswer
    private var _forthAnswer=MutableLiveData<String>()


    val nextQuestion: LiveData<Int>
    get()=_nextQuestion

    val questionStences: LiveData<String>
    get()=_questionSentences

    fun nextQuestion(){
        _nextQuestion.value=(_nextQuestion.value ?: 0)+1
    }

    fun shuffleListOfQuestions(allQuestions:List<QuizTable>) {

        shuffledQuestions= allQuestions.shuffled()
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


    fun noEmptyQuestions():Boolean{
        if(shuffledQuestions.isNotEmpty()){
            bindQuestions(_nextQuestion.value!!)
            return true

        }else{
            return false
        }
    }

    fun bindQuestions(nextQuestion:Int){
        _questionsSentence.value= shuffledQuestions[nextQuestion].questionSentence


        //binding.questionText.text=lista?.get(gameViewModel.nextQuestion.value!!)?.questionSentence.toString()
        //var options=gameViewModel.shuffleOptions(lista?.get(gameViewModel.nextQuestion.value!!))
        val options=shuffleOptions(shuffledQuestions[nextQuestion])

        _firstAsnwer.value=options[0]
        _secondAnswer.value=options[1]
        _tirthAnswer.value=options[2]
        _forthAnswer.value=options[3]

    }





}