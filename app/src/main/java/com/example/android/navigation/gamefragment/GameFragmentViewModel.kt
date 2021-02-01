package com.example.android.navigation.gamefragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.navigation.database.QuizDatabaseDao
import com.example.android.navigation.database.QuizTable
import kotlinx.coroutines.withTimeoutOrNull

class GameFragmentViewModel(
        val database: QuizDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    var allRandomQuestions = database.getAllQuestions()
    private lateinit var shuffledQuestions: List<QuizTable>


    private var numberGood=0


    var _itemQuestion = MutableLiveData<Int?>()
    val itemQuestion: LiveData<Int?>
        get() = _itemQuestion

    fun nextQuestion() {
        if (_itemQuestion.value == null) {
            _itemQuestion.value = 1
        } else {
            _itemQuestion.value = _itemQuestion.value?.plus(1)
        }
        bindQuestions(_itemQuestion.value)
    }



    var _result=MutableLiveData<Boolean?>()
    val result:LiveData<Boolean?>
        get()=_result


    val questionSentence: LiveData<String>
        get() = _questionsSentence
    private var _questionsSentence = MutableLiveData<String>()

    val firstAsnwer: LiveData<String>
        get() = _firstAsnwer
    private var _firstAsnwer = MutableLiveData<String>()

    val secondAnswer: LiveData<String>
        get() = _secondAnswer
    private var _secondAnswer = MutableLiveData<String>()

    val tirthAnswer: LiveData<String>
        get() = _tirthAnswer
    private var _tirthAnswer = MutableLiveData<String>()

    val forthAnswer: LiveData<String>
        get() = _forthAnswer
    private var _forthAnswer = MutableLiveData<String>()

    private var size=0


    fun shuffleListOfQuestions(allQuestions: List<QuizTable>) {

        shuffledQuestions = allQuestions.shuffled()
        size = shuffledQuestions.size
    }

    fun shuffleOptions(quizTable: QuizTable): List<String> {
        var lista = mutableListOf<String>()
        lista.add(quizTable.correctanswer)
        lista.add(quizTable.wronganswerone)
        lista.add(quizTable.wronganswertwo)
        lista.add(quizTable.wronganswerthree)
        lista.shuffle()

        return lista
    }


    fun bindQuestions(itemQuestion: Int?) {
        if (itemQuestion != null && itemQuestion<size) {
            _questionsSentence.value = shuffledQuestions[itemQuestion].questionSentence
            val options = shuffleOptions(shuffledQuestions[itemQuestion])

            _firstAsnwer.value = options[0]
            _secondAnswer.value = options[1]
            _tirthAnswer.value = options[2]
            _forthAnswer.value = options[3]
        }
    }

    fun check(selectedoption: String){
        var iqual:Boolean?=false
        if(itemQuestion.value==null){
            iqual=shuffledQuestions[0].correctanswer==selectedoption
        }else{
            if(itemQuestion!!.value!!<size){
                iqual=shuffledQuestions[itemQuestion.value!!].correctanswer==selectedoption
            }
        }
        if (iqual!!){
            numberGood++
        }
        _result.value=iqual

    }

    fun askforwin():Boolean{

        return numberGood== size
    }





}


