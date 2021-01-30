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

package com.example.android.navigation.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiestion_and_answers_table")
data class QuizTable(
        @PrimaryKey(autoGenerate = true)
        var questionId: Long = 0L,

        @ColumnInfo(name = "question_sentences")
        var questionSentence: String="Question sentences",

        @ColumnInfo(name = "correct_answer")
        var correctanswer: String="Correct answer",

        @ColumnInfo(name = "wrong_answer_one")
        var wronganswerone: String="wrong answer one",

        @ColumnInfo(name = "wrong_answer_two")
        var wronganswertwo: String="wrong answer two",

        @ColumnInfo(name = "wrong_answer_three")
        var wronganswerthree: String="wrong answer three"
)
