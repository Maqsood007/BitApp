package com.bitfinex.bitapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bitfinex.bitapp.data.db.tables.TODO
import com.mauritzjarl.fitnessapp.data.db.dao.DaoSteps

/**
 * Created by Muhammad Maqsood on 29/09/2020.
 */

@Database(entities = [TODO::class], version = 1, exportSchema = false)
@TypeConverters(TimestampConverter::class)
abstract class BitAppDatabase : RoomDatabase() {
    abstract fun getDaoSteps(): DaoSteps
}