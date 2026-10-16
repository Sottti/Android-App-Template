package com.sottti.android.app.template.app

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.sottti.android.app.template.domain.characters.fixtures.fixtureCharacter1
import com.sottti.android.app.template.presentation.design.system.top.bars.ui.MAIN_TOP_BAR_BACK_NAVIGATION_TEST_TAG
import com.sottti.android.app.template.presentation.design.system.top.bars.ui.MAIN_TOP_BAR_TEST_TAG
import com.sottti.android.app.template.presentation.utils.test.OrientationTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test

@HiltAndroidTest
internal class AppFlowsTest(
    orientation: OrientationTest,
) : IntegrationTest(orientation) {

    @Test
    fun characterList_clickCharacter_characterDetails_navigateBack() {
        with(rule) {
            waitUntil() {
                onAllNodes(hasText(fixtureCharacter1.name.value))
                    .fetchSemanticsNodes()
                    .isNotEmpty()
            }

            onNodeWithText(fixtureCharacter1.name.value)
                .assertIsDisplayed()

            onNodeWithText(fixtureCharacter1.name.value).performClick()

            waitForIdle()

            onNodeWithTag(MAIN_TOP_BAR_BACK_NAVIGATION_TEST_TAG).assertIsDisplayed()
            onNode(
                hasText(fixtureCharacter1.name.value) and
                    hasAnyAncestor(hasTestTag(MAIN_TOP_BAR_TEST_TAG))
            ).assertIsDisplayed()

            waitForIdle()

            onNodeWithTag(MAIN_TOP_BAR_BACK_NAVIGATION_TEST_TAG).performClick()

            waitForIdle()

            onNodeWithText(fixtureCharacter1.name.value)
                .assertIsDisplayed()
        }
    }
}
