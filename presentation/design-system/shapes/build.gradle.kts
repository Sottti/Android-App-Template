plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.sottti.android.app.template.presentation.design.system.shapes"
}

dependencies {
    implementation(libs.compose.material)
    implementation(platform(libs.compose.bom))
    implementation(projects.presentation.designSystem.dimensions)
}
