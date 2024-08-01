package com.android.finance.helper.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.android.finance.helper.model.CapturedText
import org.junit.Rule
import org.junit.Test

class DocumentItemTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun documentItemShowTitle() {
    val document = CapturedText(id = 1, text = "Sample Document")

    composeTestRule.setContent {
      DocumentItem(
        document = document,
        onItemClick = {},
        editDocument = {},
        deleteDocument = {}
      )
    }

    composeTestRule.onNodeWithText("Sample Document").assertIsDisplayed()
  }

  @Test
  fun clickDocumentItem() {
    val document = CapturedText(id = 1, text = "Sample Document")
    var clickedDocument: CapturedText? = null

    composeTestRule.setContent {
      DocumentItem(
        document = document,
        onItemClick = { clickedDocument = it },
        editDocument = {},
        deleteDocument = {}
      )
    }

    composeTestRule.onNodeWithText("Documento 1").performClick()
    assert(clickedDocument == document)
  }

  @Test
  fun documentItemContextMenuDeleteClick() {
    val document = CapturedText(id = 1, text = "Sample Document")
    var deletedDocument: CapturedText? = null

    composeTestRule.setContent {
      DocumentItem(
        document = document,
        onItemClick = {},
        editDocument = {},
        deleteDocument = { deletedDocument = it }
      )
    }

    composeTestRule.onNodeWithText("Documento 1").performClick()

    composeTestRule.onNodeWithText("Excluir").performClick()
    assert(deletedDocument == document)
  }
}