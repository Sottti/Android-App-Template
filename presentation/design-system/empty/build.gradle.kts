plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.sottti.android.app.template.presentation.design.system.empty"
}

dependencies {
    api(projects.presentation.designSystem.illustrations)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(platform(libs.compose.bom))
    implementation(projects.presentation.designSystem.colors)
    implementation(projects.presentation.designSystem.informative)
    implementation(projects.presentation.designSystem.themes)
    implementation(projects.presentation.fixtures)
    implementation(projects.presentation.previews)
    testImplementation(projects.presentation.paparazzi)
}
