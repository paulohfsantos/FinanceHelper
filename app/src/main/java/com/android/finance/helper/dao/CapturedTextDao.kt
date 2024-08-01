package com.android.finance.helper.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.finance.helper.model.CapturedText
import kotlinx.coroutines.flow.Flow

@Dao
interface CapturedTextDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertText(capturedText: CapturedText)

  @Query("SELECT * FROM captured_texts")
  fun getAllTexts(): Flow<List<CapturedText>>

  @Query("SELECT * FROM captured_texts WHERE id = :id")
  fun getTextById(id: Long): Flow<CapturedText>

  @Query("DELETE FROM captured_texts WHERE id = :id")
  suspend fun deleteTextById(id: Long)
}