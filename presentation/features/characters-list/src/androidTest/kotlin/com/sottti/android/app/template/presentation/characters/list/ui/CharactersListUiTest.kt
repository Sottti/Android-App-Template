package com.sottti.android.app.template.presentation.characters.list.ui

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasScrollToIndexAction
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import com.sottti.android.app.template.presentation.characters.list.fixtures.listOfMultipleCharactersUiModels
import com.sottti.android.app.template.presentation.design.system.empty.EMPTY_TEST_TAG
import com.sottti.android.app.template.presentation.design.system.error.ERROR_TEST_TAG
import com.sottti.android.app.template.presentation.design.system.top.bars.ui.MAIN_TOP_BAR_TEST_TAG
import com.sottti.android.app.template.presentation.utils.test.ComposeUiTest
import com.sottti.android.app.template.presentation.utils.test.FontScalesTest
import com.sottti.android.app.template.presentation.utils.test.OrientationTest
import org.junit.Test

internal class CharactersListUiTest(
    fontScale: FontScalesTest,
    orientation: OrientationTest,
) : ComposeUiTest(fontScale, orientation) {

    @Test
    fun givenStateIsInitialLoad_thenFullScreenProgressBarsAreVisible() =
        runUiTest(content = { CharactersListUiPreview(loadingState) }) {
            onNode(fullscreenIndicatorMatcher()).assertIsDisplayed()
            onNode(gridIndicatorMatcher()).assertDoesNotExist()

            onNodeWithTag(PULL_TO_REFRESH_TEST_TAG).assertIsDisplayed()
            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
        }

    @Test
    fun givenStateIsAppendLoading_thenAppendIndicatorAndCharactersAreVisible() =
        runUiTest(content = { CharactersListUiPreview(loadedStateAppendLoading) }) {
            onNode(fullscreenIndicatorMatcher()).assertDoesNotExist()
            onNode(gridIndicatorMatcher()).assertIsDisplayed()

            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
            onAllNodesWithTag(GRID_CHARACTER_TEST_TAG).assertCountEquals(2)
        }

    @Test
    fun givenStateIsPrependLoading_thenPrependIndicatorAndCharactersAreVisible() =
        runUiTest(content = { CharactersListUiPreview(loadedStatePrependLoading) }) {
            onNode(fullscreenIndicatorMatcher()).assertDoesNotExist()
            onNode(gridIndicatorMatcher()).assertIsDisplayed()

            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
            onAllNodesWithTag(GRID_CHARACTER_TEST_TAG).assertCountEquals(2)
        }

    @Test
    fun givenStateIsAppendPrependLoading_thenAppendAndPrependIndicatorsAndCharactersAreVisible() =
        runUiTest(content = { CharactersListUiPreview(loadedStateAppendPrependBothLoading) }) {
            onNode(fullscreenIndicatorMatcher()).assertDoesNotExist()
            onAllNodes(gridIndicatorMatcher()).assertCountEquals(2)
            onAllNodesWithTag(GRID_CHARACTER_TEST_TAG).assertCountEquals(2)
            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
        }

    @Test
    fun givenStateIsAppendError_thenAppendErrorAndCharactersAreVisible() =
        runUiTest(content = { CharactersListUiPreview(loadedStateAppendError) }) {
            onNode(fullscreenIndicatorMatcher()).assertDoesNotExist()
            onNode(gridIndicatorMatcher()).assertDoesNotExist()

            onNodeWithTag(PAGINATION_ERROR_TEST_TAG).assertIsDisplayed()
            onAllNodesWithTag(GRID_CHARACTER_TEST_TAG).assertCountEquals(2)
            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
        }

    @Test
    fun givenStateIsPrependError_thenPrependErrorAndCharactersAreVisible() =
        runUiTest(content = { CharactersListUiPreview(loadedStatePrependError) }) {
            onNode(fullscreenIndicatorMatcher()).assertDoesNotExist()
            onNode(gridIndicatorMatcher()).assertDoesNotExist()

            onNodeWithTag(PAGINATION_ERROR_TEST_TAG).assertIsDisplayed()
            onAllNodesWithTag(GRID_CHARACTER_TEST_TAG).assertCountEquals(2)
            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
        }

    @Test
    fun givenStateIsLoadedNoPagination_thenCharactersAreVisible() =
        runUiTest(content = { CharactersListUiPreview(loadedStateNoPagination) }) {
            onNode(fullscreenIndicatorMatcher()).assertDoesNotExist()
            onNode(gridIndicatorMatcher()).assertDoesNotExist()
            onAllNodesWithTag(GRID_CHARACTER_TEST_TAG).assertCountEquals(2)
            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
        }

    @Test
    fun givenStateIsLoadedStateAppendEndReached_thenCharactersAreVisible() =
        runUiTest(content = { CharactersListUiPreview(loadedStateAppendEndReached) }) {
            onNode(fullscreenIndicatorMatcher()).assertDoesNotExist()
            onNode(gridIndicatorMatcher()).assertDoesNotExist()
            onAllNodesWithTag(GRID_CHARACTER_TEST_TAG).assertCountEquals(2)
            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
        }

    @Test
    fun givenStateIsLoadedStatePrependEndReached_thenCharactersAreVisible() =
        runUiTest(content = { CharactersListUiPreview(loadedStatePrependEndReached) }) {
            onNode(fullscreenIndicatorMatcher()).assertDoesNotExist()
            onNode(gridIndicatorMatcher()).assertDoesNotExist()
            onAllNodesWithTag(GRID_CHARACTER_TEST_TAG).assertCountEquals(2)
            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
        }

    @Test
    fun givenStateIsLoadedStateAppendPrependBothEndsReached_thenCharactersAreVisible() =
        runUiTest(content = { CharactersListUiPreview(loadedStateAppendPrependBothEndsReached) }) {
            onNode(fullscreenIndicatorMatcher()).assertDoesNotExist()
            onNode(gridIndicatorMatcher()).assertDoesNotExist()
            onAllNodesWithTag(GRID_CHARACTER_TEST_TAG).assertCountEquals(2)
            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
        }

    @Test
    fun givenStateIsLoadedStateLoadsOfCharacters_thenCharactersAreVisible() =
        runUiTest(content = { CharactersListUiPreview(loadedStateLoadsOfCharacters) }) {
            onNode(fullscreenIndicatorMatcher()).assertDoesNotExist()
            onNode(gridIndicatorMatcher()).assertDoesNotExist()
            onNodeWithText(listOfMultipleCharactersUiModels.first().name).assertIsDisplayed()
            onNode(hasScrollToIndexAction()).performScrollToIndex(listOfMultipleCharactersUiModels.lastIndex)
            onNodeWithText(listOfMultipleCharactersUiModels.last().name).assertIsDisplayed()
            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
        }

    @Test
    fun givenStateIsLoadedStateLoadsOfCharactersRefreshing_thenCharactersAreVisible() =
        runUiTest(content = { CharactersListUiPreview(loadedStateLoadsOfCharactersRefreshing) }) {
            onNode(fullscreenIndicatorMatcher()).assertDoesNotExist()
            onNode(gridIndicatorMatcher()).assertIsNotDisplayed()

            onNodeWithTag(PULL_TO_REFRESH_TEST_TAG).assertIsDisplayed()
            onNodeWithText(listOfMultipleCharactersUiModels.first().name).assertIsDisplayed()
            onNode(hasScrollToIndexAction()).performScrollToIndex(listOfMultipleCharactersUiModels.lastIndex)
            onNodeWithText(listOfMultipleCharactersUiModels.last().name).assertIsDisplayed()
            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
        }

    @Test
    fun givenStateIsEmptyState_thenEmptyIsVisible() =
        runUiTest(content = { CharactersListUiPreview(emptyState) }) {
            onNode(fullscreenIndicatorMatcher()).assertDoesNotExist()
            onNode(gridIndicatorMatcher()).assertDoesNotExist()
            onAllNodesWithTag(GRID_CHARACTER_TEST_TAG).assertCountEquals(0)
            onNodeWithTag(EMPTY_TEST_TAG).assertIsDisplayed()
            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
        }

    @Test
    fun givenStateIsErrorState_thenErrorIsVisible() =
        runUiTest(content = { CharactersListUiPreview(errorState) }) {
            onNode(fullscreenIndicatorMatcher()).assertDoesNotExist()
            onNode(gridIndicatorMatcher()).assertDoesNotExist()
            onAllNodesWithTag(GRID_CHARACTER_TEST_TAG).assertCountEquals(0)
            onNodeWithTag(ERROR_TEST_TAG).assertIsDisplayed()
            onNodeWithTag(MAIN_TOP_BAR_TEST_TAG).assertIsDisplayed()
        }
}
