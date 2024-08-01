package com.android.finance.helper.ui.views

import androidx.compose.runtime.Composable
import com.android.finance.helper.model.viewmodel.CameraViewModel
import com.android.finance.helper.ui.components.CameraContent

@Composable
fun CameraScreen(viewModel: CameraViewModel) {
  CameraContent(viewModel.capturedTextDao)
}