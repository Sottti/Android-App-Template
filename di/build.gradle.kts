plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.sottti.android.app.template.di"
}

dependencies {
    implementation(libs.hilt)
    implementation(projects.data.characters)
    implementation(projects.data.network)
    implementation(projects.data.settings)
    implementation(projects.data.systemFeatures)
    implementation(projects.domain.characters)
    implementation(projects.domain.settings)
    implementation(projects.domain.systemFeatures)
    ksp(libs.hilt.compiler)
}
