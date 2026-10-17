plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.sottti.android.app.template.data.settings"
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.hilt)
    implementation(projects.domain.settings)
    implementation(projects.domain.systemFeatures)
    implementation(projects.utils.lifecycle)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.robolectric)
    testImplementation(libs.test.core)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
}
