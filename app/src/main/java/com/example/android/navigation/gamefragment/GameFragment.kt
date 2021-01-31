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

import android.app.Activity
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.navigation.R
import com.example.android.navigation.database.QuizDatabase
import com.example.android.navigation.database.QuizTable
import com.example.android.navigation.databinding.FragmentGameBinding

class GameFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var allquestions:List<QuizTable>
        var chosenAnswers=0


        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
                inflater, R.layout.fragment_game, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = QuizDatabase.getInstance(application).quizDatabaseDao
        val viewModelFactory = GameFragmentViewModelFactory(dataSource, application)

        val gameViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(GameFragmentViewModel::class.java)

        binding.gameViewModel = gameViewModel
        binding.lifecycleOwner = this



        gameViewModel.allRandomQuestions.observe(viewLifecycleOwner, Observer {
            it?.let {
                gameViewModel.shuffleListOfQuestions(it)
                if(!gameViewModel.noEmptyQuestions()){
                    Toast.makeText(activity, "Saved- ", Toast.LENGTH_SHORT).show()
                }
            }
        })

        gameViewModel.nextQuestion.observe(viewLifecycleOwner, Observer {
            gameViewModel.bindQuestions(it)
        })

        /*binding.submitButton.setOnClickListener{
            val index=binding.questionRadioGroup.checkedRadioButtonId
            val radioButton=binding.questionRadioGroup.findViewById<RadioButton>(index).text.toString()

            if( gameViewModel.nextQuestion.value!!<lista.size){
            val correct=lista?.get(gameViewModel.nextQuestion.value!!)?.correctanswer.toString()
            val prove=radioButton.equals(correct)

            Log.i("son iguales", prove.toString())
            if(prove){
                chosenAnswers=chosenAnswers+1
            }else{
                chosenAnswers=0
                findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
            }


            gameViewModel.nextQuestion()
            }
        }

        gameViewModel.nextQuestion.observe(viewLifecycleOwner, Observer {
            Log.i("item",gameViewModel.nextQuestion.value!!.toString())
            if(it>0 && it<lista.size){
                binding.questionText.text=lista?.get(gameViewModel.nextQuestion.value!!)?.questionSentence.toString()
                var options=gameViewModel.shuffleOptions(lista?.get(gameViewModel.nextQuestion.value!!))

                binding.firstAnswerRadioButton.text=options.get(0)
                binding.secondAnswerRadioButton.text=options.get(1)
                binding.thirdAnswerRadioButton.text=options.get(2)
                binding.fourthAnswerRadioButton.text=options.get(3)
            }
            if(it>0 && chosenAnswers==lista.size){
                chosenAnswers=0
                findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(3,3))
            }

        })*/


        return binding.root
    }

}
