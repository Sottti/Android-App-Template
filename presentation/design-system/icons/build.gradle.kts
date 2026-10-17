plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.sottti.android.app.template.presentation.design.system.icons"
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.material)
    implementation(platform(libs.compose.bom))
    implementation(projects.presentation.designSystem.colors)
    implementation(projects.presentation.designSystem.dimensions)
    implementation(projects.presentation.designSystem.iconResources)
    implementation(projects.presentation.designSystem.shapes)
    implementation(projects.presentation.designSystem.text)
    implementation(projects.presentation.designSystem.themes)
    implementation(projects.presentation.previews)
    implementation(projects.presentation.utils)
    testImplementation(projects.presentation.paparazzi)
}
