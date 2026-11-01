require "yaml"
require "minitest/autorun"

# YAML 1.1 interprets the Actions key `on` as true.
class WeeklyCiTest < Minitest::Test
  ROOT = File.expand_path("../..", __dir__)
  SCHEDULE = [{ "cron" => "17 3 * * 2", "timezone" => "Europe/Madrid" }].freeze
  DEPENDABOT_SCHEDULE = {
    "interval" => "weekly", "day" => "tuesday", "time" => "03:17",
    "timezone" => "Europe/Madrid"
  }.freeze

  def config(path)
    YAML.load_file(File.join(ROOT, path))
  end

  def test_all_workflows_are_weekly_or_manual_only
    paths = Dir[File.join(ROOT, ".github/workflows/*.{yml,yaml}")]
    assert_equal %w[android.yml codeql.yml], paths.map { |p| File.basename(p) }.sort
    paths.each do |path|
      workflow = YAML.load_file(path)
      events = workflow.fetch("on") { workflow.fetch(true) }
      assert_equal %w[schedule workflow_dispatch], events.keys.sort, path
      assert_equal SCHEDULE, events.fetch("schedule"), path
      workflow.fetch("jobs").each_value do |job|
        # No PR payload condition that silently skips scheduled/manual jobs.
        assert_equal "github.ref == 'refs/heads/develop'", job.fetch("if"), path
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

  def test_dependabot_updates_are_weekly_without_automatic_rebases
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
