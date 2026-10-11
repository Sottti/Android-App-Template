# Android App Template (AAT)

[![Android CI](https://github.com/Sottti/Android-App-Template/actions/workflows/android.yml/badge.svg)](https://github.com/Sottti/Android-App-Template/actions/workflows/android.yml)
![API](https://img.shields.io/badge/dynamic/toml?url=https://raw.githubusercontent.com/Sottti/Android-App-Template/refs/heads/develop/gradle/libs.versions.toml&query=$.versions.minSdk&label=API&color=brightgreen&suffix=%2B&logo=android&logoColor=white)
![Compose BOM](https://img.shields.io/badge/dynamic/toml?url=https://raw.githubusercontent.com/Sottti/Android-App-Template/refs/heads/develop/gradle/libs.versions.toml&query=$.versions.compose-bom&label=Compose%20BOM&color=007ACC&logo=jetpackcompose&logoColor=white)
![Navigation](https://img.shields.io/badge/dynamic/toml?url=https://raw.githubusercontent.com/Sottti/Android-App-Template/refs/heads/develop/gradle/libs.versions.toml&query=$.versions.navigation&label=Navigation&color=4CAF50&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/dynamic/toml?url=https://raw.githubusercontent.com/Sottti/Android-App-Template/refs/heads/develop/gradle/libs.versions.toml&query=$.versions.kotlin&label=Kotlin&color=7F52FF&logo=kotlin&logoColor=white)
![GitHub last commit](https://img.shields.io/github/last-commit/Sottti/Android-App-Template?logo=github&logoColor=white)
![GitHub repo size](https://img.shields.io/github/repo-size/Sottti/Android-App-Template)
[![License](https://img.shields.io/badge/License-MIT-blue?style=flat&logo=opensourceinitiative&logoColor=white)](LICENSE)

A Kotlin and Jetpack Compose starter app with modular architecture, offline caching, and reusable
test fakes.

## ⚠️ Disclaimer

This repository intentionally demonstrates a production-ready architecture suited for teams that
need to scale quickly and collaborate across multiple features. Elements such as multi-module
structure, repository and use-case layers, and other advanced patterns are included to showcase how
large, collaborative projects can stay maintainable over time. They may be unnecessary for smaller
apps or teams with different preferences. Evaluate each practice against your team's size, project
scope, and delivery goals before adopting it wholesale.

## 🧭 Overview

This project serves as a starter kit for new Android applications. It's designed to save you from
the repetitive setup process of configuring dependencies, setting up architecture, and implementing
common utilities. Use the template, rename the package and feature placeholders, then start building
your app's features.

## 📷 Screenshots

### 💡 Light Theme

<p align="center">
   <img src="https://github.com/user-attachments/assets/b2f91f07-502e-4870-828a-f55718ebcb40" alt="Characters list screen in light theme" width="32%"/>
   <img src="https://github.com/user-attachments/assets/9be4f529-7a55-4af7-b0a6-7c372948330f" alt="Character details screen in light theme" width="32%"/>
</p>
<p align="center">
   <img src="https://github.com/user-attachments/assets/198fd950-fbc5-49a8-959c-8c7b37463f6e" alt="Characters list screen in light theme landscape" width="48%"/>
   <img src="https://github.com/user-attachments/assets/759dba38-48b5-4539-947f-c68e19bce883" alt="Character details screen in light theme landscape" width="48%"/>
</p>
<p align="center">
   <img src="https://github.com/user-attachments/assets/0da05dbf-795f-4fca-990b-d035c47aa34f" alt="Characters list screen in light theme with large font scale" width="48%"/>
</p>

### 🌙 Dark Theme

<p align="center">
   <img src="https://github.com/user-attachments/assets/4b93be8d-586b-4eaf-90ec-51f69f25366a" alt="Characters list screen in dark theme" width="32%"/>
   <img src="https://github.com/user-attachments/assets/88d117c8-81ae-4f42-938b-c4b68d2d6afa" alt="Character details screen in dark theme" width="32%"/>
</p>
<p align="center">
   <img src="https://github.com/user-attachments/assets/e5b2b42f-f7c4-4ab7-bac4-d1641aa6faf6" alt="Characters list screen in dark theme landscape" width="48%"/>
   <img src="https://github.com/user-attachments/assets/029a3036-4864-42b4-9ae2-817d3def3ab9" alt="Character details screen in dark theme landscape" width="48%"/>
</p>
<p align="center">
   <img src="https://github.com/user-attachments/assets/8cdab79c-c894-468f-8cbc-36301fe813fe" alt="Characters list screen in dark theme with large font scale" width="48%"/>
</p>

## ✨ Features

This template is packed with modern libraries and tools from the Android ecosystem:

* **Tech Stack:** 100% [Kotlin](https://kotlinlang.org/)
* **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) for declarative UI.
    * **Theming:** [Material 3](https://m3.material.io/) (Material You) support.
    * **Navigation:** [Compose Navigation 3](https://developer.android.com/guide/navigation/navigation-3)
      for all screen transitions.
* **Architecture:** Follows Google's official "Guide to app architecture".
    * [MVVM](https://developer.android.com/jetpack/guide) (Model-View-ViewModel).
    * **UI Layer:** State-driven UI with `ViewModel`, `State`, and `Actions`.
    * **Domain Layer:** (Optional but recommended) for business logic.
    * **Data Layer:** `Repository` pattern.
* **Asynchronous work:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
  & [Flows](https://developer.android.com/kotlin/flow)
  for managing background threads and streams of data.
* **Dependency Injection:** [Hilt](https://dagger.dev/hilt/) for managing dependencies.
* **Networking:** [Ktor Client](https://ktor.io/docs/client-create-and-configure.html) for efficient REST API
  communication.
* **Serialization:** [Kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) (used
  with Ktor) for fast and modern JSON parsing.
* **Build Configuration:**
  Kotlin [explicit API mode](https://kotlinlang.org/docs/whatsnew15.html#explicit-api-mode) is
  enabled across modules to keep public APIs intentional and well-documented.
* **Testing:**
    * **Unit Tests:** [JUnit 4](https://junit.org/junit4/)
    * **Screenshot Tests:** [Paparazzi](https://github.com/cashapp/paparazzi)
    * **UI Tests:** [Compose Test Rules](https://developer.android.com/jetpack/compose/testing)
      testing both screen orientations and multiple font scales.
    * **Integration Tests:** Instrumented Compose flows cover the full stack—from
      navigation orchestration to feature screens using Hilt-enabled end-to-end journeys and
      parameterized scenarios that sweep multiple font scales and orientations
      via the shared `BaseUiTest` harness.
    * **Test Doubles Philosophy:** The template deliberately avoids runtime
      mocking frameworks. Instead, it ships purpose-built *fakes* that behave
      like lightweight in-memory implementations of production contracts.
      This approach keeps tests fast and deterministic, encourages explicit
      collaboration boundaries, and allows your team to reuse the same fakes
      across unit, integration, and end-to-end scenarios without the brittleness
      often introduced by mocks.

### ▶️ Running the test suites

Execute the following Gradle tasks from the project root to run the automated test suites locally:

> ℹ️ Run Gradle with JDK 21 or newer.

| Suite | Command | Description |
| --- | --- | --- |
| Local/unit tests | `./gradlew test -PexcludeSnapshotTests=true` | Runs local tests in Android and plain Kotlin/JVM modules, excluding screenshot tests. |
| Screenshot tests | `./gradlew verifyPaparazziDebug` | Verifies debug Paparazzi screenshots against the checked-in baselines without an emulator. |
| Instrumented Android tests | `./gradlew connectedDebugAndroidTest` | Launches Espresso/Compose instrumentation tests in `src/androidTest` on a connected emulator or device. |

The local/unit and screenshot commands match the suites run by the [CI workflow](.github/workflows/android.yml).

> ℹ️ Ensure that an Android emulator or physical device is connected before running the
> instrumentation suite.

### 🌟 Additional Highlights

* **Dynamic system theming:** The `presentation` layer streams system theme and contrast updates,
  maps them through the `data/system-features` module, and applies them across the Compose UI while
  synchronizing system bars for a fully adaptive experience.
* **Offline-first paging:** The `data:characters` module layers Room DAOs, Paging 3, and a
  `RemoteMediator` on top of the Ktor client so character feeds stay cached and transparently refresh
  whenever the network is available.
* **Navigation infrastructure:** A dedicated Compose Navigation 3 stack coordinates screen changes
  via a `NavigationManager`, with `Navigator` components observing command channels and handling
  saveable state, back stack pops, and simultaneous navigation requests. Navigation contracts and
  destination models live in `presentation:navigation`; `presentation:navigation-impl` owns the
  Navigation 3 implementation and feature wiring. Feature modules depend on the contracts and stay
  decoupled from the implementation details.
* **Snapshot tooling:** The `presentation` module includes a Paparazzi test toolkit featuring a
  custom Pixel 10 Pro XL device profile and helpers that generate day/night parameter sets for rich
  screenshot coverage.
* **Preview ergonomics:** `presentation:previews` packages reusable Compose `@Preview` annotations
  so feature teams can opt into consistent device sizes, light/dark themes, and dynamic color
  toggles without re-declaring boilerplate in every screen file.
* **Coroutine utilities:** The `utils` module ships reusable coroutine extensions such as
  `stateInWhileSubscribed` and `observeConfigurationChanges` for concise and lifecycle-aware state
  management in ViewModels.
* **Build hygiene:** Shared Gradle configuration enables Kotlin explicit API mode and context
  parameters. Detekt and Android lint provide static analysis, Kover provides coverage checks, and
  module dependency checks enforce architectural boundaries.
* **Testable system features:** The `data/system-features` module exposes fake managers backed by
  Turbine-based tests, showcasing how to stub system services when exercising the settings feature.
* **Streamlined startup:** A Hilt-enabled `Application`, splash activity, and edge-to-edge
  `AppShellActivity` combine with the navigation manager to launch directly into themed Compose
  content with minimal boilerplate.
* **Edge-to-edge ready:** Compose screens are rendered behind the system bars, with window insets
  managed centrally so features automatically inherit full-height layouts.

### 🔭 Incoming Features

* **Multipane large-screen layouts:** Adaptive list-detail experiences that keep the character catalog
  and the selected detail visible side by side on tablets, foldables, and landscape desktops.

## 🏛️ Architecture (MVVM + Clean)

This template uses a state-driven MVVM (Model-View-ViewModel) architecture combined with principles
from Clean Architecture.

* **UI (Compose)**: Observes `State` from the `ViewModel` and sends `Actions` (user actions) to it.
  It is passive and dumb.
* **ViewModel**: Follows
  a [declarative state holder approach](https://developer.android.com/topic/architecture/ui-layer/stateholders).
  Handles business logic for the screen. It consumes `Actions`, interacts with
  UseCases/Repositories, and exposes a single `State` Flow for the UI to observe.
* **UseCases (Domain Layer)**: (Optional) Encapsulates a single piece of business logic (e.g.,
  `GetCharacter`). This makes logic reusable and easier to test.
* **Repository (Data Layer)**: The single source of truth for data. It abstracts away the data
  source (network or local database) and provides a clean API for the `ViewModel` or `UseCases` to
  consume.

## 🏗️ Project Structure

The repository is laid out as a layered, multi-module Gradle project. Each directory below maps to a
distinct slice of the architecture so teams can develop features independently while keeping
dependencies explicit.

```
├── app/                     # Android entry point and app-level configuration
├── data/                    # Repository implementations, remote clients, and system services
├── domain/                  # Business rules, models, and use cases shared by features
├── di/                      # Centralized Hilt bindings that wire modules together
├── presentation/            # Compose UI, navigation stack, previews, and design system
├── utils/                   # Cross-cutting helpers (coroutines, lifecycle, etc.)
├── gradle/                  # Gradle wrapper and dependency version catalog
├── build.gradle.kts         # Shared module configuration and verification tasks
└── settings.gradle.kts      # Repositories and module list
```

Shared build configuration lives in [`build.gradle.kts`](build.gradle.kts), and dependency versions
are defined in [`gradle/libs.versions.toml`](gradle/libs.versions.toml).

### 🗺️ Module Overview

Every Gradle module has a single responsibility. Use the table below to find the code you need:

* **Application shell**
    * `app`: Hosts the `Application`, activities, and wires the dependency graph at runtime.
* **Presentation layer**
    * `presentation:design-system`: Shared Compose theming, typography, and reusable components.
    * `presentation:app-shell`: Owns the app shell and top-level screen composition.
    * `presentation:features:`: Feature-specific screens such as `characters-list` and
      `character-details`.
    * `presentation:navigation` & `presentation:navigation-impl`: Navigation contracts and their
      Compose Navigation 3 implementation.
    * `presentation:paparazzi`, `presentation:previews`, `presentation:utils`,
      `presentation:fixtures`:
      Tooling for previews, snapshot tests, and sample data.
* **Domain layer**
    * `domain:core-models`: Canonical models exchanged between layers.
    * `domain:characters`, `domain:settings`, `domain:system-features`: Use cases and business logic per
      feature area.
* **Data layer**
    * `data:characters`: Paging, Room cache, and remote mediator for list and detail flows.
    * `data:network`: Ktor client configuration and API definitions.
    * `data:settings`: Persistence for user preferences and configuration toggles.
    * `data:system-features`: Abstractions over device capabilities with test fakes.
* **Dependency injection**
    * `di`: Shared Hilt modules and component wiring consumed across the app.
* **Shared utilities**
    * `utils:lifecycle`: Lifecycle-aware coroutine helpers and Flow extensions reused in ViewModels.

## 🚀 How to Use This Template

1. **Create Your Repository:**
   Click the "Use this template" button on the GitHub repository page. This will create a new
   repository in your account with the same file structure.

2. **Clone Your New Repository:**
   ```bash
   git clone https://github.com/<your-github-user>/<your-repo>.git
   cd <your-repo>
   ```

3. **Rename Package Name:**
   The current package name is `com.sottti.android.app.template`. To rename it:
    * In Android Studio, right-click the root `com.sottti.android.app.template` package in the
      `kotlin` directory.
    * Select **Refactor > Rename**.
    * In the dialog, choose **Rename package**.
    * Enter your new package name (e.g., `com.mycompany.myapp`).
    * Click **Do Refactor**.
    * Manually update the `applicationId` in `app/build.gradle.kts` to match your new package name.

4. **Sync & Build:**
   Sync the Gradle files and build the project to ensure everything is set up correctly.

5. **Start Coding!**
   You're all set. Start building your app's unique features.

## 🤝 Contributing

This is a template for *your* projects, but if you have ideas on how to improve the template itself,
contributions are welcome!

See [CONTRIBUTING.md](CONTRIBUTING.md) for the contribution workflow and pull request expectations.

## 📜 License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT) - see
the [LICENSE](LICENSE) file for details.

## 👋 Contact

If you feel like saying hi, have any comments, suggestions or
questions, [open an issue](https://github.com/Sottti/Android-App-Template/issues?q=sort%3Aupdated-desc+is%3Aissue+is%3Aopen)
or say [hi 👋 on X](https://x.com/Sotttti).
