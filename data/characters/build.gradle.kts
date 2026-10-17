plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.sottti.android.app.template.data.characters"
}

dependencies {
    implementation(libs.hilt)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.paging.runtime)
    implementation(libs.room)
    implementation(libs.room.paging)
    implementation(projects.data.network)
    implementation(projects.domain.characters)
    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.paging.testing)
    testImplementation(libs.robolectric)
    testImplementation(libs.test.core)
    testImplementation(libs.truth)
}
