package com.example.android.navigation


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test fun addNewQuestion() {
        onView(withId(R.id.newButton)).perform(click())

        onView(withId(R.id.newquestionText)).perform(typeText("pregunta"))
        closeSoftKeyboard()
        onView(withId(R.id.correctAnswerEditText)).perform(typeText("respuesta"))
        closeSoftKeyboard()
        onView(withId(R.id.wrongOneEditText)).perform(typeText("mal 1"))
        closeSoftKeyboard()
        onView(withId(R.id.wrongTwoEditText)).perform(typeText("mal 2"))
        closeSoftKeyboard()
        onView(withId(R.id.wrongThreeEditText)).perform(typeText("mal 3"))
        closeSoftKeyboard()

        onView(withId(R.id.newsubmitButton)).perform(click())
    }

    @Test fun winplay() {
        onView(withId(R.id.playButton)).perform(click())

        onView(withText("respuesta")).perform(click())

        onView(withId(R.id.submitButton)).perform(click())

    }

    @Test fun loseplay() {
        onView(withId(R.id.playButton)).perform(click())

        onView(withText("mal 1")).perform(click())

        onView(withId(R.id.submitButton)).perform(click())

    }



}