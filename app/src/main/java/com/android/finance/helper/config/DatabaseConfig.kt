package com.android.finance.helper.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.finance.helper.dao.CapturedTextDao
import com.android.finance.helper.model.CapturedText

@Database(entities = [CapturedText::class], version = 1)
abstract class DatabaseConfig : RoomDatabase() {
    abstract fun capturedTextDao(): CapturedTextDao
}