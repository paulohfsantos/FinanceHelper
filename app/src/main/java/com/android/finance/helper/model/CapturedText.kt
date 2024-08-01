package com.android.finance.helper.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "captured_texts")
data class CapturedText(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val text: String
)
