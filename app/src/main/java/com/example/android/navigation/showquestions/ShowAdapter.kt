package com.example.android.navigation.showquestions

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import android.widget.TextView
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.navigation.R
import com.example.android.navigation.database.QuizTable
import com.example.android.navigation.databinding.TextItemViewBinding
import com.example.android.navigation.utils.TextItemViewHolder

class ShowAdapter(val clickListener: ListListener): PagedListAdapter<QuizTable, ShowAdapter.ViewHolder>(QuizDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }




    class ViewHolder private constructor(val binding: TextItemViewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: QuizTable, clickListener: ListListener) {
            binding.sentences = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TextItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


}

class QuizDiffCallback : DiffUtil.ItemCallback<QuizTable>() {

    override fun areItemsTheSame(oldItem: QuizTable, newItem: QuizTable): Boolean {
        return oldItem.questionId == newItem.questionId
    }

    override fun areContentsTheSame(oldItem: QuizTable, newItem: QuizTable): Boolean {
        return oldItem == newItem
    }
}





class ListListener(val clickListener: (quizId: Long) -> Unit) {
    fun onClick(quiz: QuizTable) = clickListener(quiz.questionId)
}








