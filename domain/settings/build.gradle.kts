plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    api(projects.domain.coreModels)
    api(libs.annotations)
    api(libs.kotlin.coroutines.core)
    implementation(libs.javax.inject)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
}
