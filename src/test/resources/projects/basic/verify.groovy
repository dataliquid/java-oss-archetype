// Verify the generated project
def projectDir = new File(basedir, "project/test-project")

try {
    println "Starting verification for: ${basedir}"
    
    // Check basic structure
    println "Checking project directory exists: ${projectDir}"
    assert projectDir.exists() : "Project directory should exist: ${projectDir}"
    
    println "Checking pom.xml..."
    assert new File(projectDir, "pom.xml").exists() : "pom.xml should exist"
    
    println "Checking LICENSE..."
    assert new File(projectDir, "LICENSE").exists() : "LICENSE should exist"
    
    println "Checking .gitignore..."
    assert new File(projectDir, ".gitignore").exists() : ".gitignore should exist"
    println "PASS: Basic structure validated"
    
    // Check GitHub workflows
    println "Checking GitHub workflows..."
    assert new File(projectDir, ".github/workflows").exists() : ".github/workflows should exist"
    assert new File(projectDir, ".github/workflows/ci.yml").exists() : "CI workflow should exist"
    assert new File(projectDir, ".github/workflows/dependency-check.yml").exists() : "Dependency check workflow should exist"
    assert new File(projectDir, ".github/workflows/gitflow-release.yml").exists() : "Gitflow release workflow should exist"
    assert new File(projectDir, ".github/workflows/gitflow-hotfix.yml").exists() : "Gitflow hotfix workflow should exist"
    println "PASS: GitHub workflows validated"
    
    // Check source directories
    println "Checking source directories..."
    assert new File(projectDir, "src/main/java/com/example/test/App.java").exists() : "Main App.java should exist"
    assert new File(projectDir, "src/test/java/com/example/test/AppTest.java").exists() : "Test AppTest.java should exist"
    println "PASS: Source directories validated"
    
    println "VERIFICATION PASSED"
    return true
} catch (AssertionError e) {
    println "ERROR: ASSERTION FAILED: ${e.message}"
    throw e
} catch (Exception e) {
    println "ERROR: UNEXPECTED: ${e.message}"
    e.printStackTrace()
    throw e
}