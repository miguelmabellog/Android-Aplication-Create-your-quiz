package com.example.android.navigation.gamefragment

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.SystemClock
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.android.navigation.database.QuizDatabaseDao
import com.example.android.navigation.database.QuizTable
import com.example.android.navigation.reciver.AlarmReceiver
import com.example.android.navigation.R
import com.example.android.navigation.utils.cancelNotifications
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import kotlinx.coroutines.withTimeoutOrNull

class GameFragmentViewModel(
        val database: QuizDatabaseDao,
        application: Application) : AndroidViewModel(application) {

    var allRandomQuestions: LiveData<PagedList<QuizTable>> = database.getAllQuestions().toLiveData(pageSize = 10)
    private lateinit var shuffledQuestions: List<QuizTable>


    private var numberGood = 0


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


        resetTimer()
        setAlarm(true)


    }


    var _result = MutableLiveData<Boolean?>()
    val result: LiveData<Boolean?>
        get() = _result


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

    private var size = 0


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
        if (itemQuestion != null && itemQuestion < size) {
            _questionsSentence.value = shuffledQuestions[itemQuestion].questionSentence
            val options = shuffleOptions(shuffledQuestions[itemQuestion])

            _firstAsnwer.value = options[0]
            _secondAnswer.value = options[1]
            _tirthAnswer.value = options[2]
            _forthAnswer.value = options[3]
        }
    }

    fun check(selectedoption: String) {
        var iqual: Boolean? = false
        if (itemQuestion.value == null) {
            iqual = shuffledQuestions[0].correctanswer == selectedoption
        } else {
            if (itemQuestion!!.value!! < size) {
                iqual = shuffledQuestions[itemQuestion.value!!].correctanswer == selectedoption
            }
        }
        if (iqual!!) {
            numberGood++
        }
        _result.value = iqual


    }

    fun askforwin(): Boolean {

        return numberGood == size
    }

    private val app = application
    private var _alarmOn = MutableLiveData<Boolean>(false)
    val isAlarmOn: LiveData<Boolean>
        get() = _alarmOn

    private val REQUEST_CODE = 0
    private val notifyIntent = Intent(app, AlarmReceiver::class.java)

    private val notifyPendingIntent: PendingIntent

    private val timerLengthOptions: IntArray

    private lateinit var timer: CountDownTimer

    private val second: Long = 1_000L

    private val _elapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long>
        get() = _elapsedTime

    private var prefs =
            app.getSharedPreferences("com.example.android.navigation", Context.MODE_PRIVATE)

    private val TRIGGER_TIME = "TRIGGER_AT"

    private val minute: Long = 60_000L

    private val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    init {
        /*_alarmOn.value = PendingIntent.getBroadcast(
                getApplication(),
                REQUEST_CODE,
                notifyIntent,
                PendingIntent.FLAG_NO_CREATE
        ) != null*/

        notifyPendingIntent = PendingIntent.getBroadcast(
                getApplication(),
                REQUEST_CODE,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        timerLengthOptions = app.resources.getIntArray(R.array.minutes_array)

        if (_alarmOn.value!!) {
            createTimer()
        }


        setAlarm(true)
    }

    fun setAlarm(isChecked: Boolean) {
        when (isChecked) {
            true -> {
                startTimer(0)
            }
            false -> cancelNotification()
        }
    }


        private fun createTimer() {
        viewModelScope.launch {
            val triggerTime = loadTime()
            timer = object : CountDownTimer(triggerTime, second) {
                override fun onTick(millisUntilFinished: Long) {
                    _elapsedTime.value = triggerTime - SystemClock.elapsedRealtime()
                    if (_elapsedTime.value!! <= 0) {
                        resetTimer()
                    }
                }

                override fun onFinish() {
                    resetTimer()
                }
            }
            timer.start()
        }
    }

    private fun resetTimer() {
        timer.cancel()
        _elapsedTime.value = 0
        _alarmOn.value = false
    }

    private suspend fun loadTime(): Long =
            withContext(Dispatchers.IO) {
                prefs.getLong(TRIGGER_TIME, 0)
            }

    private suspend fun saveTime(triggerTime: Long) =
            withContext(Dispatchers.IO) {
                prefs.edit().putLong(TRIGGER_TIME, triggerTime).apply()
            }

    private fun cancelNotification() {
        resetTimer()
        alarmManager.cancel(notifyPendingIntent)
    }

    private fun startTimer(timerLengthSelection: Int) {
        _alarmOn.value?.let {
            if (!it) {
                _alarmOn.value = false
                val selectedInterval = when (timerLengthSelection) {
                    0 -> second * 10 //For testing only
                    else ->timerLengthOptions[timerLengthSelection] * minute
                }
                val triggerTime = SystemClock.elapsedRealtime() + selectedInterval


                val notificationManager =
                        ContextCompat.getSystemService(
                                app,
                                NotificationManager::class.java
                        ) as NotificationManager

                notificationManager.cancelNotifications()

                AlarmManagerCompat.setExactAndAllowWhileIdle(
                        alarmManager,
                        AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        triggerTime,
                        notifyPendingIntent
                )


                viewModelScope.launch {
                    saveTime(triggerTime)
                }
            }
        }
        createTimer()
    }

}


