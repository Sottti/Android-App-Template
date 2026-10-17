plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.paparazzi)
}

android {
    namespace = "com.sottti.android.app.template.presentation.design.system.images"
}

dependencies {
    api(projects.domain.coreModels)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.annotations)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.ktor3)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    implementation(platform(libs.compose.bom))
    implementation(projects.presentation.designSystem.dimensions)
    implementation(projects.presentation.designSystem.progressIndicators)
    implementation(projects.presentation.designSystem.shapes)
    implementation(projects.presentation.designSystem.themes)
    implementation(projects.presentation.fixtures)
    implementation(projects.presentation.previews)
    testImplementation(projects.presentation.paparazzi)
}
