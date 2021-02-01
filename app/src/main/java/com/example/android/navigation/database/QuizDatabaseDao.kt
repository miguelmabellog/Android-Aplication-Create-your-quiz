

package com.example.android.navigation.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface QuizDatabaseDao {

    @Insert
    suspend fun insert(night: QuizTable)

    @Update
    suspend fun update(night: QuizTable)

    @Query("SELECT * from quiestion_and_answers_table WHERE questionId = :key")
    suspend fun get(key: Long): QuizTable?

    @Query("DELETE FROM quiestion_and_answers_table")
    suspend fun clear()

    @Query("DELETE FROM quiestion_and_answers_table WHERE questionId = :key")
    suspend fun deleteId(key: Long)

    @Query("SELECT * FROM quiestion_and_answers_table ORDER BY questionId DESC")
    fun getAllQuestions(): DataSource.Factory<Int, QuizTable>

    @Query("SELECT * FROM quiestion_and_answers_table ORDER BY questionId DESC LIMIT 1")
    suspend fun getTonight(): QuizTable?


}

