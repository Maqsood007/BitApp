package com.bitfinex.bitapp.data.db.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bitfinex.bitapp.data.db.TimestampConverter
import java.util.*

@Entity
data class TODO(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "date_time")
    @TypeConverters(TimestampConverter::class)
    val dateTime: Date?,

    @ColumnInfo(name = "todo") val steps: Long?
)