enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "AndroidAppTemplate"

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
include(
    ":app",
    ":data:characters",
    ":data:network",
    ":data:settings",
    ":data:system-features",
    ":di",
    ":domain:core-models",
    ":domain:characters",
    ":domain:settings",
    ":domain:system-features",
    ":presentation:design-system:colors",
    ":presentation:design-system:dimensions",
    ":presentation:design-system:empty",
    ":presentation:design-system:error",
    ":presentation:design-system:icon-resources",
    ":presentation:design-system:icons",
    ":presentation:design-system:illustrations",
    ":presentation:design-system:images",
    ":presentation:design-system:informative",
    ":presentation:design-system:progress-indicators",
    ":presentation:design-system:shapes",
    ":presentation:design-system:text",
    ":presentation:design-system:themes",
    ":presentation:design-system:top-bars",
    ":presentation:design-system:typography",
    ":presentation:app-shell",
    ":presentation:features:character-details",
    ":presentation:features:characters-list",
    ":presentation:fixtures",
    ":presentation:navigation",
    ":presentation:navigation-impl",
    ":presentation:paparazzi",
    ":presentation:previews",
    ":presentation:string-provider",
    ":presentation:utils",
    ":utils:lifecycle",
)
