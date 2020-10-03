package com.example.android.navigation.gamefragment

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.navigation.R
import com.example.android.navigation.database.QuizDatabaseDao
import com.example.android.navigation.database.QuizTable
import kotlinx.coroutines.launch

class GameFragmentViewModel(
        val database: QuizDatabaseDao,
        application: Application) : AndroidViewModel(application) {


    private var tonight = MutableLiveData<QuizTable?>()

    private val _navigateToSleepQuality = MutableLiveData<QuizTable>()
    val navigateToSleepQuality: LiveData<QuizTable>
        get() = _navigateToSleepQuality

    data class Question(
            val text: String,
            val answers: List<String>)

    private val questions: MutableList<GameFragment.Question> = mutableListOf(
            GameFragment.Question(text = "What is Android Jetpack?",
                    answers = listOf("all of these", "tools", "documentation", "libraries")),
            GameFragment.Question(text = "Base class for Layout?",
                    answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")),
            GameFragment.Question(text = "Layout for complex Screens?",
                    answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")),
            GameFragment.Question(text = "Pushing structured data into a Layout?",
                    answers = listOf("Data Binding", "Data Pushing", "Set Text", "OnClick")),
            GameFragment.Question(text = "Inflate layout in fragments?",
                    answers = listOf("onCreateView", "onViewCreated", "onCreateLayout", "onInflateLayout")),
            GameFragment.Question(text = "Build system for Android?",
                    answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")),
            GameFragment.Question(text = "Android vector format?",
                    answers = listOf("VectorDrawable", "AndroidVectorDrawable", "DrawableVector", "AndroidVector")),
            GameFragment.Question(text = "Android Navigation Component?",
                    answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")),
            GameFragment.Question(text = "Registers app with launcher?",
                    answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")),
            GameFragment.Question(text = "Mark a layout for Data Binding?",
                    answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>"))
    )

    lateinit var currentQuestion: GameFragment.Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }


    private fun setQuestion() {
        currentQuestion = questions[questionIndex]

        answers = currentQuestion.answers.toMutableList()

        answers.shuffle()
        //(activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }

    fun onStopTracking() {
        viewModelScope.launch {
            // In Kotlin, the return@label syntax is used for specifying which function among
            // several nested ones this statement returns from.
            // In this case, we are specifying to return from launch(),
            // not the lambda.
            val oldNight = tonight.value ?: return@launch

            // Update the night in the database to add the end time.
            oldNight.endTimeMilli = System.currentTimeMillis()

            update(oldNight)

            // Set state to navigate to the SleepQualityFragment.
            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun update(night: QuizTable) {
        database.update(night)
    }


}