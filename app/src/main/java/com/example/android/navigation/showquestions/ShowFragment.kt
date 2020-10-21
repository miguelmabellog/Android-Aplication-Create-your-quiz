package com.example.android.navigation.showquestions

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.android.navigation.R
import com.example.android.navigation.database.QuizDatabase
import com.example.android.navigation.databinding.FragmentShowBinding
import com.example.android.navigation.utils.SpactinItemDecorator


class ShowFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding:  FragmentShowBinding= DataBindingUtil.inflate(
                inflater, R.layout.fragment_show, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = QuizDatabase.getInstance(application).quizDatabaseDao

        val viewModelFactory = ShowViewModelFactory(dataSource, application)

        val showViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(ShowViewModel::class.java)

        binding.showViewModel = showViewModel

        // binding.setLifecycleOwner(this)
        binding.lifecycleOwner = this


        val adapter = ShowAdapter()
        binding.listofquestions.adapter = adapter
        binding.listofquestions.setOnClickListener {
            
        }

        var space= SpactinItemDecorator(10)


        binding.listofquestions.addItemDecoration(space)

        showViewModel.allQuestions.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })


        return binding.root
    }




}