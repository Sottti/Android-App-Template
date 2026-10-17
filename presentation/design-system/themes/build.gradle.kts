plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.sottti.android.app.template.presentation.design.system.themes"
}

dependencies {
    implementation(libs.compose.material)
    implementation(libs.material)
    implementation(libs.splashscreen)
    implementation(platform(libs.compose.bom))
    implementation(projects.domain.coreModels)
    implementation(projects.presentation.designSystem.colors)
    implementation(projects.presentation.designSystem.dimensions)
    implementation(projects.presentation.designSystem.iconResources)
    implementation(projects.presentation.designSystem.shapes)
    implementation(projects.presentation.designSystem.typography)
}
