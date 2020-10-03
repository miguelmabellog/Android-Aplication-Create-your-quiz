package com.example.android.navigation.newQuestion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.navigation.R
import com.example.android.navigation.TitleFragmentDirections
import com.example.android.navigation.database.QuizDatabase
import com.example.android.navigation.databinding.FragmentNewQuestionBinding
import com.example.android.navigation.databinding.FragmentTitleBinding


/**
 * A simple [Fragment] subclass.
 * Use the [NewQuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewQuestionFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentNewQuestionBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_new_question, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = QuizDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory = NewQuestionViewModelFactory(dataSource, application)

        val newQuizViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(NewQuestionViewModel::class.java)

        binding.newquestionviewmodel = newQuizViewModel




        binding.newsubmitButton.setOnClickListener { v: View ->
            v.findNavController().navigate(NewQuestionFragmentDirections.actionNewQuestionFragmentToTitleFragment()) }

        return binding.root
    }


}