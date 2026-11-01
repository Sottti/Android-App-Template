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

## Weekly and manual verification

Android verification and advanced CodeQL (`actions` and `java-kotlin`) run once
weekly on Tuesday at 03:17 Europe/Madrid, or by explicit manual dispatch, on
trusted `develop`. The IANA timezone preserves local time across DST; GitHub may
delay a scheduled run. Dependabot version updates use the same weekly slot and
automatic rebasing is disabled.

PR creation, updates, pushes and merges must not start Actions or CI, including
managed CodeQL, indirect callers, comment commands and merge queues. Required
CI status checks must not be reintroduced in rulesets or classic protection.
Keep PR requirements, conversation resolution, force-push/deletion safeguards,
security alerts, independent review and local verification. Weekly results do
not verify individual PRs: regressions may be discovered after merge.

Before editing workflow policy, run its regression checks:

```bash
ruby .github/scripts/test_weekly_ci.rb
```

For manual recovery, select **Run workflow** on **Android CI** or **CodeQL** with
`develop`, or use `gh workflow run android.yml --ref develop` and
`gh workflow run codeql.yml --ref develop`. Other refs deliberately skip jobs.
Inspect each run's exact SHA, job/step outcomes and reports. A skipped job is not
a pass, and runner/billing admission failure is not test execution. Do not add
automatic retries. A successful manual run does not prove the scheduler fired.

Use the owner's existing GitHub Actions failure-email notifications. Verify the
actual notification settings and recipient in GitHub Settings → Notifications;
workflow YAML cannot configure or prove email delivery. Keep first genuine
weekly execution proof pending until an actual `schedule` run is observed.

When migrating an existing repository, inventory registered workflows, CodeQL
default setup, effective rulesets/classic protection and all open PR branches.
Disable obsolete registrations while transitioning, remove only the retired CI
requirements from current protection, and retain the other rules. Disable CodeQL
default setup before enabling advanced analysis. Reconcile old PR workflow files
before re-enabling workflows; old PRs can otherwise still trigger their copies.
Dependabot PRs opened before `rebase-strategy: disabled` can continue rebasing
for 30 days. Preserve their updates and add a maintainer commit that reconciles
the policy; Dependabot stops automatic rebasing after maintainer commits. Do not
use Dependabot skip markers in those commit messages or manually recreate those
PRs without rechecking the policy.

The shared policy and restoration instructions in
[Sottti/.github#13](https://github.com/Sottti/.github/issues/13) must agree before
rollout is considered complete. Never restore the superseded PR-CI requirements.

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
