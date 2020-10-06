

package com.example.android.navigation.database

import androidx.lifecycle.LiveData
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

    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param night new value to write
     */
    @Update
    suspend fun update(night: QuizTable)

    /**
     * Selects and returns the row that matches the supplied start time, which is our key.
     *
     * @param key startTimeMilli to match
     */
    @Query("SELECT * from quiestion_and_answers_table WHERE questionId = :key")
    suspend fun get(key: Long): QuizTable?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM quiestion_and_answers_table")
    suspend fun clear()

    /**
     * Selects and returns all rows in the table,
     *
     * sorted by start time in descending order.
     */
    @Query("SELECT * FROM quiestion_and_answers_table ORDER BY questionId DESC")
    fun getAllNights(): LiveData<List<QuizTable>>

    /**
     * Selects and returns the latest night.
     */
    @Query("SELECT * FROM quiestion_and_answers_table ORDER BY questionId DESC LIMIT 1")
    suspend fun getTonight(): QuizTable?


}

