package com.android.finance.helper.config.textrecognition

import android.media.Image
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TextRecognitionAnalyzer(
  private val onDetectedTextUpdated: (String) -> Unit
) : ImageAnalysis.Analyzer {

  companion object {
    const val THROTTLE_TIMEOUT_MS = 1_000L
  }

  private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
  private val textRecognizer: TextRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

  @OptIn(ExperimentalGetImage::class)
  override fun analyze(imageProxy: ImageProxy) {
    scope.launch {
      val mediaImage = extractImage(imageProxy) ?: run { imageProxy.close(); return@launch }
      val inputImage = createInputImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

      processImage(inputImage)?.let { detectedText ->
        if (detectedText.isNotBlank()) {
          onDetectedTextUpdated(detectedText)
        }
      }

      delay(THROTTLE_TIMEOUT_MS)
    }.invokeOnCompletion { exception ->
      exception?.printStackTrace()
      imageProxy.close()
    }
  }

  @OptIn(ExperimentalGetImage::class)
  private fun extractImage(imageProxy: ImageProxy): Image? {
    return imageProxy.image
  }

  private fun createInputImage(mediaImage: Image, rotationDegrees: Int): InputImage {
    return InputImage.fromMediaImage(mediaImage, rotationDegrees)
  }

  private suspend fun processImage(inputImage: InputImage): String? = suspendCoroutine { continuation ->
    textRecognizer.process(inputImage)
      .addOnSuccessListener { visionText: Text ->
        continuation.resume(visionText.text)
      }
      .addOnFailureListener { exception ->
        exception.printStackTrace()
        continuation.resume(null)
      }
  }
}