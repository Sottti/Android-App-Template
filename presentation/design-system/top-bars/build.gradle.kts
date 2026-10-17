plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.sottti.android.app.template.presentation.design.system.top.bars"
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    implementation(platform(libs.compose.bom))
    implementation(projects.presentation.designSystem.icons)
    implementation(projects.presentation.designSystem.text)
    implementation(projects.presentation.designSystem.themes)
    implementation(projects.presentation.fixtures)
    implementation(projects.presentation.previews)
    testImplementation(projects.presentation.paparazzi)
}
