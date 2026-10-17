plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.sottti.android.app.template.app"

    defaultConfig {
        applicationId = "com.sottti.android.app.template"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.sottti.android.app.template.app.HiltTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

// compose.ui.test.junit4 forces runner 1.5.0 with BoM version 2025.11.01
configurations
    .matching { configuration ->
        configuration.name.contains("AndroidTest", ignoreCase = true)
    }
    .configureEach {
        resolutionStrategy {
            force("androidx.test:runner:1.7.0")
        }
    }

dependencies {
    constraints {
        androidTestImplementation(libs.test.espresso.core) {
            because("Espresso before 3.7.0 reflects InputManager.getInstance, unavailable on API 37.")
        }
    }

    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.test.core)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(testFixtures(projects.data.network))
    androidTestImplementation(testFixtures(projects.presentation.utils))
    debugImplementation(libs.compose.ui.test.manifest)
    implementation(libs.appcompat)
    implementation(libs.coil.compose)
    implementation(libs.compose.runtime)
    implementation(libs.hilt)
    implementation(libs.material)
    implementation(libs.splashscreen)
    implementation(platform(libs.compose.bom))
    implementation(projects.di)
    implementation(projects.domain.characters)
    implementation(projects.presentation.designSystem.iconResources)
    implementation(projects.presentation.designSystem.themes)
    implementation(projects.presentation.designSystem.topBars)
    implementation(projects.presentation.appShell)
    implementation(projects.presentation.utils)
    ksp(libs.hilt.compiler)
    kspAndroidTest(libs.hilt.compiler)
}
