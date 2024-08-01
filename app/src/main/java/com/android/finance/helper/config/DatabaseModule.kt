package com.android.finance.helper.config

import android.content.Context
import androidx.room.Room
import com.android.finance.helper.dao.CapturedTextDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
  @Provides
  @Singleton
  fun provideDatabase(@ApplicationContext appContext: Context): DatabaseConfig {
    return Room.databaseBuilder(
      appContext,
      DatabaseConfig::class.java,
      "finance_helper_db"
    ).build()
  }

  @Provides
  fun provideCapturedTextDao(database: DatabaseConfig): CapturedTextDao {
    return database.capturedTextDao()
  }
}