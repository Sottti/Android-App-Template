plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.sottti.android.app.template.presentation.fixtures"
}

dependencies {
    implementation(projects.domain.coreModels)
}
