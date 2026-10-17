plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.sottti.android.app.template.presentation.design.system.illustrations"
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.material)
    implementation(platform(libs.compose.bom))
    implementation(projects.presentation.designSystem.dimensions)
    implementation(projects.presentation.designSystem.themes)
    implementation(projects.presentation.previews)
    testImplementation(projects.presentation.paparazzi)
}
