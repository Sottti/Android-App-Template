plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.sottti.android.app.template.presentation.navigation.impl"
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

    implementation(libs.compose.foundation)
    implementation(libs.compose.material)
    implementation(libs.hilt)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.lifecycle.viewModel.navigation)
    implementation(libs.navigation.runtime)
    implementation(libs.navigation.ui)
    implementation(platform(libs.compose.bom))
    implementation(projects.presentation.designSystem.colors)
    implementation(projects.presentation.designSystem.themes)
    implementation(projects.presentation.features.characterDetails)
    implementation(projects.presentation.features.charactersList)
    implementation(projects.presentation.navigation)
    ksp(libs.hilt.compiler)

    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.kotlin.coroutines.test)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
}
