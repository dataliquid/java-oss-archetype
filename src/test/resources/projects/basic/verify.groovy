// Verify the generated project
def projectDir = new File(basedir, "project/test-project")

// Check basic structure
assert projectDir.exists() : "Project directory should exist: ${projectDir}"
assert new File(projectDir, "pom.xml").exists() : "pom.xml should exist"
assert new File(projectDir, "LICENSE").exists() : "LICENSE should exist"
assert new File(projectDir, ".gitignore").exists() : ".gitignore should exist"

// Check GitHub workflows
assert new File(projectDir, ".github/workflows").exists() : ".github/workflows should exist"
assert new File(projectDir, ".github/workflows/ci.yml").exists() : "CI workflow should exist"
assert new File(projectDir, ".github/workflows/dependency-check.yml").exists() : "Dependency check workflow should exist"
assert new File(projectDir, ".github/workflows/gitflow-release.yml").exists() : "Gitflow release workflow should exist"
assert new File(projectDir, ".github/workflows/gitflow-hotfix.yml").exists() : "Gitflow hotfix workflow should exist"

// Check source directories
assert new File(projectDir, "src/main/java/com/example/test/App.java").exists() : "Main App.java should exist"
assert new File(projectDir, "src/test/java/com/example/test/AppTest.java").exists() : "Test AppTest.java should exist"

println "Integration test passed: All expected files were created!"
return true