package com.android.finance.helper.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.finance.helper.dao.CapturedTextDao
import com.android.finance.helper.model.CapturedText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(val capturedTextDao: CapturedTextDao) : ViewModel() {
  suspend fun insertText(text: String) {
    capturedTextDao.insertText(CapturedText(text = text))
  }

  // StateFlow to hold the list of CapturedText
  private val _documentList = MutableStateFlow(emptyList<CapturedText>())
  val documentList: StateFlow<List<CapturedText>> = _documentList.asStateFlow()

  init {
    viewModelScope.launch {
      capturedTextDao.getAllTexts().collect { texts ->
        _documentList.value = texts
      }
    }
  }
}