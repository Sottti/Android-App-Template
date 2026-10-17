plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.sottti.android.app.template.presentation.characters.list"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    constraints {
        androidTestImplementation(libs.test.espresso.core) {
            because("Espresso before 3.7.0 reflects InputManager.getInstance, unavailable on API 37.")
        }
    }

    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(libs.truth)
    androidTestImplementation(testFixtures(projects.presentation.utils))
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.lifecycle.viewModel)
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)
    implementation(platform(libs.compose.bom))
    implementation(projects.domain.characters)
    implementation(projects.presentation.designSystem.colors)
    implementation(projects.presentation.designSystem.dimensions)
    implementation(projects.presentation.designSystem.empty)
    implementation(projects.presentation.designSystem.error)
    implementation(projects.presentation.designSystem.images)
    implementation(projects.presentation.designSystem.progressIndicators)
    implementation(projects.presentation.designSystem.shapes)
    implementation(projects.presentation.designSystem.text)
    implementation(projects.presentation.designSystem.themes)
    implementation(projects.presentation.designSystem.topBars)
    implementation(projects.presentation.navigation)
    implementation(projects.presentation.previews)
    implementation(projects.presentation.utils)
    ksp(libs.hilt.compiler)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.paging.testing)
    testImplementation(libs.robolectric)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
    testImplementation(projects.presentation.paparazzi)
}
