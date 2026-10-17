plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    api(projects.domain.coreModels)
    api(libs.kotlin.coroutines.core)
    api(libs.paging.common)
    implementation(libs.javax.inject)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.paging.testing)
    testImplementation(libs.truth)
}
