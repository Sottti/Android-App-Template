# Instructions

- This repository is a multi-module Android app project built with Gradle.
- Prefer idiomatic Kotlin and follow Kotlin coding conventions.
- Prefer idiomatic Gradle usage and follow Gradle best practices.
- Prefer idiomatic Android development practices and follow Android best practices.
- Use Hilt for dependency injection.
- Use Jetpack Compose for UI development.
- Use kotlin coroutines for asynchronous programming.
- Prefer multiplatform solutions where applicable.

## Creating GitHub issues and pull requests

Before composing an issue or PR title or body, read the current
[shared contribution guide](https://github.com/Sottti/.github/blob/main/CONTRIBUTING.md)
and the appropriate template from `Sottti/.github` on `main`:

```sh
gh api 'repos/Sottti/.github/contents/CONTRIBUTING.md?ref=main' -H 'Accept: application/vnd.github.raw+json'
# For a bug report:
gh api 'repos/Sottti/.github/contents/.github/ISSUE_TEMPLATE/bug_report.md?ref=main' -H 'Accept: application/vnd.github.raw+json'
# For other issues:
gh api 'repos/Sottti/.github/contents/.github/ISSUE_TEMPLATE/task.md?ref=main' -H 'Accept: application/vnd.github.raw+json'
# For a pull request:
gh api 'repos/Sottti/.github/contents/.github/pull_request_template.md?ref=main' -H 'Accept: application/vnd.github.raw+json'
```

Apply the shared title rules and fill the selected template before submitting.
Raw API creation does not populate templates automatically. Preserve applicable
repository-specific metadata, contribution and verification policies, and read
back the stored title and body after creation. If a shared source cannot be
read, report that limitation instead of silently assuming a local copy is current.

Follow this repository's [contribution and verification guidance](CONTRIBUTING.md).
