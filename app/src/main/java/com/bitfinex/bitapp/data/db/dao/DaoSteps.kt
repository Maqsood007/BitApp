package com.mauritzjarl.fitnessapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bitfinex.bitapp.data.db.tables.TODO

/**
 * Created by Muhammad Maqsood on 19/09/2020.
 */

@Dao
interface DaoSteps {

    @Insert
    fun insertSteps(TODO: TODO): Long

    @Query("SELECT * FROM TODO")
    suspend fun getSteps(): List<TODO>
}