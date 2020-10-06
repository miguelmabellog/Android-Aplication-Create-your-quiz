/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation.gamefragment

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.android.navigation.R
import com.example.android.navigation.database.QuizDatabase
import com.example.android.navigation.databinding.FragmentGameBinding

class GameFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = QuizDatabase.getInstance(application).quizDatabaseDao
        val viewModelFactory = GameFragmentViewModelFactory(dataSource, application)

        val GameViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(GameFragmentViewModel::class.java)

        binding.gameViewModel = GameViewModel
        binding.lifecycleOwner = this

        





        return binding.root
    }

    // randomize the questions and set the first question
    private fun randomizeQuestions() {

    }

    // Sets the question and randomizes the answers.  This only changes the data, not the UI.
    // Calling invalidateAll on the FragmentGameBinding updates the data.
    private fun setQuestion() {

    }
}
