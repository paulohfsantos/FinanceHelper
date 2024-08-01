package com.android.finance.helper.ui.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
// import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.finance.helper.dao.CapturedTextDao
import com.android.finance.helper.model.CapturedText
import com.android.finance.helper.ui.components.DocumentItem
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  onItemClick: (CapturedText) -> Unit,
  navController: NavHostController,
  capturedTextDao: CapturedTextDao
) {
  val context = LocalContext.current
  val cameraPermissionState = rememberPermissionState(
    android.Manifest.permission.CAMERA
  )
  val scope = rememberCoroutineScope()

  val onRequestPermission = cameraPermissionState::launchPermissionRequest
  val documentList by capturedTextDao.getAllTexts().collectAsState(emptyList())
//  val documentList = listOf(
//    CapturedText(1, "Document 1"),
//  )

  fun deleteDocument(document: CapturedText) {
    scope.launch {
      capturedTextDao.deleteTextById(document.id)

      Toast.makeText(context, "Document deleted", Toast.LENGTH_SHORT).show()
    }
  }

  fun editDocument(document: CapturedText) {
    // Edit document
    print("Edit document")
  }

  Surface(modifier = Modifier.fillMaxSize()) {
    Column(
      modifier = Modifier.fillMaxSize(),
    ) {
      TopAppBar(
        title = { Text(text = "Documentos") },
      )
      LazyColumn {
        if (documentList.isEmpty()) {
          item {
            Text(
              text = "No documents found",
              modifier = Modifier.padding(16.dp)
            )
          }
        } else {
          items(items = documentList) { document ->
            DocumentItem(
              document = document,
              onItemClick = { onItemClick(document) },
              editDocument = { editDocument(document) },
              deleteDocument = {
                deleteDocument(document)
              }
            )
          }
        }
      }

      FloatingActionButton(
        onClick = {
          if (cameraPermissionState.status.isGranted) {
            navController.navigate("camera")
          } else {
            onRequestPermission()
          }
        },
        modifier = Modifier
          .padding(16.dp)
          .align(Alignment.End)
      ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
      }
    }
  }
}