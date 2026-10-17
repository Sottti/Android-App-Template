plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.sottti.android.app.template.presentation.design.system.icon.resources"
}

dependencies {
    implementation(libs.appcompat)
    implementation(projects.presentation.designSystem.colors)
}
