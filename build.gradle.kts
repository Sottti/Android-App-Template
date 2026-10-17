import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import dev.detekt.gradle.Detekt
import dev.detekt.gradle.DetektCreateBaselineTask
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt) apply true
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kover) apply true
    alias(libs.plugins.paparazzi) apply false
}

tasks.register<Delete>("cleanPaparazziSnapshots") {
    group = "verification"
    description = "Deletes all Paparazzi snapshot images across the project."
    delete(fileTree(rootDir) { include("**/src/test/snapshots/images/**") })
}

val checkModuleDependencyBoundaries = tasks.register("checkModuleDependencyBoundaries") {
    group = "verification"
    description = "Checks direct project dependencies follow the configured module boundary rules."

    doLast {
        val dependencyViolations = allprojects
            .flatMap { project ->
                project.configurations.flatMap { configuration ->
                    configuration.dependencies
                        .withType(ProjectDependency::class.java)
                        .filter { dependency -> dependency.path != project.path }
                        .map { dependency ->
                            ProjectDependencyEdge(
                                configuration = configuration.name,
                                source = project.path,
                                target = dependency.path,
                            )
                        }
                }
            }
            .flatMap { dependency -> dependency.boundaryViolations() }

        val pluginViolations = allprojects
            .filter { project -> project.path in pureKotlinModules }
            .flatMap { project -> project.pureKotlinModuleViolations() }

        val violations = (dependencyViolations + pluginViolations)
            .distinct()
            .sorted()

        if (violations.isNotEmpty()) {
            throw GradleException(
                buildString {
                    appendLine("Module dependency boundary violations found:")
                    violations.forEach { violation -> appendLine("- $violation") }
                },
            )
        }
    }
}

private val excludeSnapshotTestsProperty = "excludeSnapshotTests"
private val snapshotTestPattern = "**/*SnapshotTest.class"

val libraries = the<LibrariesForLibs>()

subprojects {
    apply(plugin = "dev.detekt")

    dependencies {
        "detektPlugins"(libraries.detekt.compose)
        "detektPlugins"(libraries.detekt.formatting) {
            // Alpha.4 bundles ktlint, but its metadata still requests the unpublished repackaging artifact.
            exclude(group = "dev.detekt", module = "ktlint-repackage")
        }
    }

    detekt {
        toolVersion = libraries.versions.detekt.get()
        config.setFrom(files("$rootDir/detekt.yml"))
        buildUponDefaultConfig = true
        autoCorrect = false
        parallel = true
        baselineFile("detekt-baseline.xml")?.let { baseline = it }
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget.set("21")
        ignoreFailures = false
        exclude("**/StateInViewModelWhileSubscribed.kt")
        reports {
            html.required.set(true) // Human readable (open in browser)
            checkstyle.required.set(true) // Machine readable
            sarif.required.set(false) // Not needed locally
            markdown.required.set(false)
        }
    }

    tasks.withType<DetektCreateBaselineTask>().configureEach {
        jvmTarget.set("21")
        exclude("**/StateInViewModelWhileSubscribed.kt")
    }

    val excludeSnapshotTests = providers.gradleProperty(excludeSnapshotTestsProperty)
        .map(String::toBoolean)
        .orElse(false)

    tasks.withType<Test>().configureEach {
        if (excludeSnapshotTests.get()) {
            exclude(snapshotTestPattern)
            failOnNoDiscoveredTests.set(false)
        }
    }

    tasks.matching { it.name == "check" }.configureEach {
        dependsOn(checkModuleDependencyBoundaries)
    }

    plugins.withId("com.android.application") {
        androidApplicationConfig()
        androidKotlinConfig()
    }

    plugins.withId("com.android.library") {
        androidLibraryConfig()
        androidKotlinConfig()
    }

    plugins.withId("org.jetbrains.kotlin.jvm") {
        extensions.configure<KotlinJvmProjectExtension> {
            explicitApi()
            jvmToolchain(21)
            compilerOptions {
                freeCompilerArgs.add("-Xcontext-parameters")
            }
        }
    }
}

private fun Project.androidKotlinConfig() {
    extensions.configure<KotlinAndroidProjectExtension> {
        explicitApi()
        jvmToolchain(21)
        compilerOptions {
            freeCompilerArgs.add("-Xcontext-parameters")
            freeCompilerArgs.add("-Xannotation-default-target=param-property")
        }
    }
}

private fun Project.androidApplicationConfig() {
    extensions.configure<ApplicationExtension> {
        compileSdk = compileSdk()
        defaultConfig {
            minSdk = minSdk()
            targetSdk = targetSdk()
        }
        lint {
            baselineFile("lint-baseline.xml")?.let { baseline = it }
            abortOnError = true
            checkReleaseBuilds = false
            xmlReport = true
            htmlReport = true
        }
        packaging {
            jniLibs.keepDebugSymbols.add("**/libandroidx.graphics.path.so")
        }
    }
}

private fun Project.androidLibraryConfig() {
    extensions.configure<LibraryExtension> {
        compileSdk = compileSdk()
        defaultConfig { minSdk = minSdk() }
        lint {
            targetSdk = targetSdk()
            baselineFile("lint-baseline.xml")?.let { baseline = it }
            abortOnError = true
            xmlReport = true
            htmlReport = true
        }
    }
}

private fun Project.baselineFile(path: String) =
    file(path).takeIf { it.exists() }

private fun compileSdk() = libs.versions.compileSdk.get().toInt()
private fun minSdk() = libs.versions.minSdk.get().toInt()
private fun targetSdk() = libs.versions.targetSdk.get().toInt()

private data class ProjectDependencyEdge(
    val configuration: String,
    val source: String,
    val target: String,
)

private val dependencyFreeModules = setOf(
    ":domain:core-models",
    ":presentation:navigation",
    ":presentation:previews",
    ":presentation:string-provider",
)

private val pureKotlinModules = setOf(
    ":domain:core-models",
    ":domain:characters",
    ":domain:settings",
    ":domain:system-features",
    ":presentation:navigation",
    ":presentation:paparazzi",
)

private val pureKotlinForbiddenPluginIds = mapOf(
    "com.android.application" to "Android application",
    "com.android.library" to "Android library",
    "com.google.dagger.hilt.android" to "Hilt Android",
)

private val presentationSupportModules = setOf(
    ":presentation:fixtures",
    ":presentation:paparazzi",
    ":presentation:previews",
)

private fun ProjectDependencyEdge.boundaryViolations(): List<String> = buildList {
    if (source in dependencyFreeModules) {
        add("$source must not declare project dependencies, but $configuration depends on $target.")
    }

    if (target == ":app") {
        add("$source must not depend on :app via $configuration.")
    }

    if (source.startsWith(":domain:") && !target.startsWith(":domain:")) {
        add("$source is a domain module and may only depend on domain modules, but $configuration depends on $target.")
    }

    if (source.startsWith(":data:") && !target.isAllowedDataDependency()) {
        add("$source is a data module and may only depend on data, domain, or utils modules, but $configuration depends on $target.")
    }

    if (source.startsWith(":presentation:") && target.isForbiddenPresentationDependency()) {
        add("$source is a presentation module and must not depend on data, di, or app modules, but $configuration depends on $target.")
    }

    if (target.startsWith(":presentation:features:") && source != ":presentation:navigation-impl") {
        add("$source must not depend on feature module $target via $configuration; feature wiring belongs in :presentation:navigation-impl.")
    }

    if (source.startsWith(":presentation:features:") && target in setOf(
            ":presentation:app-shell",
            ":presentation:navigation-impl"
        )
    ) {
        add("$source is a feature module and must not depend on $target via $configuration.")
    }

    if (source == ":presentation:app-shell" && !target.isAllowedAppShellDependency()) {
        add("$source may only depend on domain settings, navigation, navigation-impl, design-system modules, or presentation utils, but $configuration depends on $target.")
    }

    if (source.startsWith(":presentation:design-system:") && target.isForbiddenDesignSystemDependency()) {
        add("$source is a design-system module and must not depend on $target via $configuration.")
    }

    if (source.isNonPresentationLayer() && target in presentationSupportModules) {
        add("$source must not depend on presentation support module $target via $configuration.")
    }

    if (source == ":di" && !target.isAllowedDiDependency()) {
        add("$source may only depend on data or domain modules, but $configuration depends on $target.")
    }
}

private fun Project.pureKotlinModuleViolations(): List<String> = buildList {
    if (!plugins.hasPlugin("org.jetbrains.kotlin.jvm")) {
        add("$path is configured as a pure Kotlin module and must apply the Kotlin JVM plugin.")
    }

    pureKotlinForbiddenPluginIds
        .filter { (pluginId, _) -> plugins.hasPlugin(pluginId) }
        .forEach { (pluginId, description) ->
            add("$path is configured as a pure Kotlin module and must not apply the $description plugin ($pluginId).")
        }
}

private fun String.isAllowedDataDependency() =
    startsWith(":data:") || startsWith(":domain:") || startsWith(":utils:")

private fun String.isForbiddenPresentationDependency() =
    startsWith(":data:") || this == ":di" || this == ":app"

private fun String.isAllowedAppShellDependency() =
    this == ":domain:settings" ||
        this == ":presentation:navigation" ||
        this == ":presentation:navigation-impl" ||
        this == ":presentation:utils" ||
        startsWith(":presentation:design-system:")

private fun String.isForbiddenDesignSystemDependency() =
    startsWith(":presentation:features:") ||
        this == ":presentation:app-shell" ||
        this == ":presentation:navigation-impl" ||
        startsWith(":data:") ||
        this == ":di" ||
        this == ":app"

private fun String.isNonPresentationLayer() =
    startsWith(":domain:") || startsWith(":data:") || this == ":di" || this == ":app"

private fun String.isAllowedDiDependency() =
    startsWith(":data:") || startsWith(":domain:")
