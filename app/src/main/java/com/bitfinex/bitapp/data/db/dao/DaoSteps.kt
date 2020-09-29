package com.mauritzjarl.fitnessapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bitfinex.bitapp.data.db.tables.Steps

/**
 * Created by Muhammad Maqsood on 19/09/2020.
 */

@Dao
interface DaoSteps {

    @Insert
    fun insertSteps(steps: Steps) : Long

    @Query("SELECT * FROM Steps")
    suspend fun getSteps() : List<Steps>
}