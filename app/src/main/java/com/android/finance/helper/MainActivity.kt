package com.android.finance.helper

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.android.finance.helper.config.DatabaseConfig
import com.android.finance.helper.dao.CapturedTextDao
import com.android.finance.helper.model.viewmodel.CameraViewModel
import com.android.finance.helper.ui.theme.FinanceHelperTheme
import com.android.finance.helper.ui.views.CameraScreen
import com.android.finance.helper.ui.views.DocumentScreen
import com.android.finance.helper.ui.views.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  @Inject lateinit var capturedTextDao: CapturedTextDao

  private val cameraPermissionRequest =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
      if (isGranted) {
        setCameraPreview()
      } else {
        // Camera permission denied
      }
    }

  // database init
  companion object {
    lateinit var database: DatabaseConfig
      private set
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    database = Room.databaseBuilder(
      applicationContext,
      DatabaseConfig::class.java,
      "finance_helper_db"
    ).build()

    when (PackageManager.PERMISSION_GRANTED) {
      ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) -> {
        setCameraPreview()
      }
      else -> {
        cameraPermissionRequest.launch(Manifest.permission.CAMERA)
      }
    }
  }

  private fun setCameraPreview() {
    setContent {
      FinanceHelperTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          MyApp()
        }
      }
    }
  }
}

@Composable
fun MyApp() {
  val navController = rememberNavController()

  NavHost(navController = navController, startDestination = "home") {
    composable("home") {
      HomeScreen(
        onItemClick = {
          navController.navigate("document?id=${it.id}")
        },
        navController = navController,
        capturedTextDao = MainActivity.database.capturedTextDao()
      )
    }

    composable("camera") {
      val capturedTextDao = MainActivity.database.capturedTextDao()
      val viewModel = remember { CameraViewModel(capturedTextDao) }

      CameraScreen(viewModel)
    }

    composable(
      route = "document?id={id}",
      arguments = listOf(
        navArgument("id") {
          type = NavType.LongType
        }
      )
    ) {
      val documentId = it.arguments?.getLong("id") ?: 0
      val capturedTextDao = MainActivity.database.capturedTextDao()
      val document = capturedTextDao.getTextById(documentId)

      DocumentScreen(
        id = documentId,
        document = document,
      )
    }
  }
}