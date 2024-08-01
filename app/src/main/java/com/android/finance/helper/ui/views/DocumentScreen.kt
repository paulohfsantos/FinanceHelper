package com.android.finance.helper.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.android.finance.helper.dao.CapturedTextDao
import com.android.finance.helper.model.CapturedText
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocumentScreen(id: Long, document: Flow<CapturedText?>) {
  val documentFound by document.collectAsState(null)

  // get first word
  val firstWord = documentFound?.text?.split(" ")?.get(0)

  Surface(modifier = Modifier.fillMaxSize()) {
    Column(modifier = Modifier.fillMaxSize()) {
      TopAppBar(
        title = { Text(text = "Document ${id.toInt()}") },
      )
      Column {
        Row {
          Text(text = "Document ID: ${documentFound?.id} - $firstWord")
        }

        Column(
          modifier = Modifier.fillMaxSize(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Text(text = "Document Text: ${documentFound?.text}")
        }
      }
    }
  }
}