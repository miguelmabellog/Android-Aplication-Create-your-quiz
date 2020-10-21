package com.example.android.navigation.showquestions

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.navigation.R
import com.example.android.navigation.database.QuizTable
import com.example.android.navigation.utils.TextItemViewHolder

class ShowAdapter:RecyclerView.Adapter<TextItemViewHolder>() {

    var data =  listOf<QuizTable>()

        set(value) {
            field = value
            notifyDataSetChanged()
        }



    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.questionSentence






        //holder.textView.setTextColor(Color.BLACK) // black

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
                .inflate(R.layout.text_item_view, parent, false) as TextView

        return TextItemViewHolder(view)
    }



}








