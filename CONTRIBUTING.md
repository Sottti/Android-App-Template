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
./gradlew checkModuleDependencyBoundaries detekt lintDebug
./gradlew test -PexcludeSnapshotTests=true
./gradlew verifyPaparazziDebug
./gradlew koverXmlReport koverVerify -PexcludeSnapshotTests=true
./gradlew assembleDebug
```

Run instrumented tests when a change affects Android runtime behavior:

```bash
./gradlew connectedDebugAndroidTest
```

## PR and manual verification

Android verification and advanced CodeQL (`actions` and `java-kotlin`) run on
every pull request, including drafts and updates. They also support explicit
manual dispatch from `develop`. There is no CI cron schedule and no push or
post-merge run. Superseded runs are cancelled per workflow and PR number;
different PRs do not cancel each other.

This is an owner-approved PR-CI exception for this public repository. Keep all
existing validation commands, reports, coverage thresholds and failure behavior.
Run local checks and independent review before merge, and inspect the actual PR
checks. Do not add required status gates or change unrelated branch protections.
Use `pull_request`, not privileged `pull_request_target` or indirect caller chains.
Keep managed CodeQL default setup disabled so it does not duplicate the advanced
CodeQL workflow.

Dependabot version updates run monthly on the first day at 03:17 Europe/Madrid,
with automatic rebasing disabled. This separate dependency-update schedule and
the existing security-update settings remain unchanged.

Before editing workflow policy, run its regression checks:

```bash
ruby .github/scripts/test_weekly_ci.rb
```

For manual recovery, select **Run workflow** on **Android CI** or **CodeQL** with
`develop`, or use `gh workflow run android.yml --ref develop` and
`gh workflow run codeql.yml --ref develop`. Other manual refs deliberately skip
jobs. Inspect each run's exact SHA, job/step outcomes and reports. A skipped job
is not a pass, and runner/billing admission failure is not test execution. Do
not add automatic retries. Local and manual results apply to their tested
revision and do not replace the actual PR result.

Use the owner's existing GitHub Actions failure-email notifications. Workflow
YAML cannot configure or prove email delivery. Keep shared policy guidance in
[Sottti/.github](https://github.com/Sottti/.github/blob/develop/branch-protection/README.md)
aligned with this exception; other repositories retain their approved cadence.

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
