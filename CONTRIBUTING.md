# Contributing

Thanks for helping improve Android App Template.

## Workflow

1. Fork the repository.
2. Create a focused branch from `develop`.
3. Keep changes small enough to review.
4. Run the relevant checks before opening a pull request.
5. Open a pull request using the template and describe the behavior change.

## Local Checks

Run the narrowest check that covers your change. Use JDK 21 or newer. For broad changes, run:

```bash
./gradlew checkModuleDependencyBoundaries detekt lintDebug test \
  -PexcludeSnapshotTests=true \
  verifyPaparazziDebug koverXmlReport koverVerify assembleDebug
```

Run instrumented tests when a change affects Android runtime behavior:

```bash
./gradlew connectedDebugAndroidTest
```

## Pull Requests

Use the [shared pull request template](https://github.com/Sottti/.github/blob/develop/.github/pull_request_template.md)
and follow the [shared issue and pull request title guidance](https://github.com/Sottti/.github/blob/develop/CONTRIBUTING.md#issue-and-pull-request-titles).
GitHub inherits the template from `Sottti/.github`; inherited files are not
included in this repository's clone. Before preparing a PR body, read the current
template from GitHub:

```sh
gh api 'repos/Sottti/.github/contents/.github/pull_request_template.md?ref=develop' -H 'Accept: application/vnd.github.raw+json'
```

Add or update tests where needed, and update screenshots or snapshots for
intentional UI changes.

Pull requests should include:

- What changed.
- Why the change is needed.
- How it was verified.
- Screenshots or recordings for visible UI changes.

Please keep unrelated formatting, generated files, and IDE metadata out of the
pull request unless they are required for the change.
