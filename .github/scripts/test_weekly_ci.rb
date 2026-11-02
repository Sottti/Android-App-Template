require "yaml"
require "minitest/autorun"

# YAML 1.1 interprets the Actions key `on` as true.
class CiPolicyTest < Minitest::Test
  ROOT = File.expand_path("../..", __dir__)
  DEPENDABOT_SCHEDULE = {
    "interval" => "monthly", "time" => "03:17",
    "timezone" => "Europe/Madrid"
  }.freeze

  def config(path)
    YAML.load_file(File.join(ROOT, path))
  end

  def test_all_workflows_run_on_every_pr_or_trusted_manual_dispatch
    paths = Dir[File.join(ROOT, ".github/workflows/*.{yml,yaml}")]
    assert_equal %w[android.yml codeql.yml], paths.map { |p| File.basename(p) }.sort
    paths.each do |path|
      workflow = YAML.load_file(path)
      events = workflow.fetch("on") { workflow.fetch(true) }
      assert_equal %w[pull_request workflow_dispatch], events.keys.sort, path
      assert_nil events.fetch("pull_request"), "Do not filter PR paths, branches or events"
      assert_nil events.fetch("workflow_dispatch"), path
      assert_equal "${{ github.workflow }}-${{ github.event.pull_request.number || github.ref }}", workflow.fetch("concurrency").fetch("group"), path
      assert_equal true, workflow.fetch("concurrency").fetch("cancel-in-progress"), path
      workflow.fetch("jobs").each_value do |job|
        # PR merge refs must run, while manual runs stay on trusted develop.
        assert_equal "github.event_name == 'pull_request' || (github.event_name == 'workflow_dispatch' && github.ref == 'refs/heads/develop')", job.fetch("if"), path
        assert_equal "ubuntu-latest", job.fetch("runs-on"), path
        refute job.key?("uses"), "Automatic reusable callers are outside this policy"
      end
    end
  end

  def test_android_verification_and_failure_reports_are_retained
    job = config(".github/workflows/android.yml").fetch("jobs").fetch("build")
    steps = job.fetch("steps")
    commands = steps.map { |s| s["run"] }.compact
    assert_equal [
      "chmod +x gradlew",
      "./gradlew --no-daemon checkModuleDependencyBoundaries",
      "./gradlew --no-daemon detekt lintDebug",
      "./gradlew --no-daemon test -PexcludeSnapshotTests=true",
      "./gradlew --no-daemon verifyPaparazziDebug",
      "./gradlew --no-daemon koverXmlReport koverVerify -PexcludeSnapshotTests=true",
      "./gradlew --no-daemon assembleDebug"
    ], commands
    refute job.key?("continue-on-error")
    steps.each { |s| refute s.key?("continue-on-error") }
    reports = steps.find { |s| s["name"] == "Upload Reports" }
    assert_equal "always()", reports.fetch("if")
    assert_equal "**/build/reports/**\n**/build/test-results/**\n", reports.fetch("with").fetch("path")
    assert_equal "always()", steps.find { |s| s["name"] == "Job Summary" }.fetch("if")
    assert_equal "21", steps.find { |s| s["name"] == "Set up JDK 21" }.fetch("with").fetch("java-version")
  end

  def test_codeql_keeps_both_languages_and_compiles_kotlin
    job = config(".github/workflows/codeql.yml").fetch("jobs").fetch("analyze")
    assert_equal [
      { "language" => "actions", "build-mode" => "none" },
      { "language" => "java-kotlin", "build-mode" => "manual" }
    ], job.fetch("strategy").fetch("matrix").fetch("include")
    assert_equal false, job.fetch("strategy").fetch("fail-fast")
    assert_equal "write", job.fetch("permissions").fetch("security-events")
    steps = job.fetch("steps")
    init = steps.index { |s| s["uses"] == "github/codeql-action/init@v4" }
    build = steps.index { |s| s["name"] == "Build Android sources for CodeQL" }
    analysis = steps.index { |s| s["uses"] == "github/codeql-action/analyze@v4" }
    assert_operator init, :<, build
    assert_operator build, :<, analysis
    assert_equal "matrix.language == 'java-kotlin'", steps[build].fetch("if")
    assert_equal "./gradlew --no-daemon --no-build-cache --no-configuration-cache --rerun-tasks assemble", steps[build].fetch("run")
    steps.each { |s| refute s.key?("continue-on-error") }
  end

  def test_dependabot_updates_are_monthly_without_automatic_rebases
    updates = config(".github/dependabot.yml").fetch("updates")
    assert_equal %w[github-actions gradle], updates.map { |u| u.fetch("package-ecosystem") }.sort
    updates.each do |update|
      assert_equal "/", update.fetch("directory")
      assert_equal DEPENDABOT_SCHEDULE, update.fetch("schedule")
      assert_equal "disabled", update.fetch("rebase-strategy")
    end
    assert_equal ["androidx.*"], updates.find { |u| u["package-ecosystem"] == "gradle" }.fetch("groups").fetch("androidx").fetch("patterns")
  end
end
