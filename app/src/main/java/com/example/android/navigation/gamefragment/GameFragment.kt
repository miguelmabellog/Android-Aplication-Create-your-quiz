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

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.android.navigation.R
import com.example.android.navigation.TitleFragmentDirections
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

        val gameViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(GameFragmentViewModel::class.java)

        binding.gameViewModel = gameViewModel
        binding.lifecycleOwner = this



        gameViewModel.allRandomQuestions.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it.isEmpty()){
                    Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()
                }else{
                    gameViewModel.shuffleListOfQuestions(it)
                    gameViewModel.bindQuestions(0)
                }
            }
        })

        binding.submitButton.setOnClickListener {
            val index=binding.questionRadioGroup.checkedRadioButtonId
            val selectedoption=binding.questionRadioGroup.findViewById<RadioButton>(index).text.toString()
            gameViewModel.check(selectedoption)
        }

        gameViewModel.result.observe(viewLifecycleOwner, Observer {
            if(it==true){
                Toast.makeText(activity, "bien", Toast.LENGTH_SHORT).show()
                gameViewModel.nextQuestion()
                if(gameViewModel.askforwin()){
                    findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment(0,0))
                }
            }
            if(it==false){
                Toast.makeText(activity, "mal", Toast.LENGTH_SHORT).show()
                findNavController().navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
            }
        })

        createChannel(
                getString(R.string.egg_notification_channel_id),
                getString(R.string.egg_notification_channel_name)
        )

        return binding.root
    }

    private fun createChannel(channelId: String, channelName: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                    channelId,
                    channelName,

                    NotificationManager.IMPORTANCE_HIGH
            )
                    .apply {
                        setShowBadge(false)
                    }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.breakfast_notification_channel_description)

            val notificationManager = requireActivity().getSystemService(
                    NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }

    }

}
