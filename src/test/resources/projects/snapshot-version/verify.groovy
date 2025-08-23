// Verify SNAPSHOT version and custom package
def projectDir = new File(basedir, "project/snapshot-app")

try {
    println "Checking project directory exists: ${projectDir}"
    assert projectDir.exists() : "Project directory does not exist: ${projectDir}"
    
    // Check POM content
    def pomFile = new File(projectDir, "pom.xml")
    println "Checking POM file: ${pomFile}"
    assert pomFile.exists() : "POM file does not exist: ${pomFile}"
    
    def pomContent = pomFile.text
    assert pomContent.contains("<groupId>com.snapshot.test</groupId>") : "POM missing correct groupId"
    assert pomContent.contains("<artifactId>snapshot-app</artifactId>") : "POM missing correct artifactId"
    assert pomContent.contains("<version>3.0.0-SNAPSHOT</version>") : "POM missing correct SNAPSHOT version"
    assert pomContent.contains("<maven.compiler.release>21</maven.compiler.release>") : "POM missing Java 21 compiler release"
    println "PASS: POM file validated"
    
    // Check package structure uses custom package parameter
    def appJavaFile = new File(projectDir, "src/main/java/com/snapshot/test/app/App.java")
    println "Checking App.java: ${appJavaFile}"
    assert appJavaFile.exists() : "App.java does not exist at: ${appJavaFile}"
    
    def testJavaFile = new File(projectDir, "src/test/java/com/snapshot/test/app/AppTest.java")
    println "Checking AppTest.java: ${testJavaFile}"
    assert testJavaFile.exists() : "AppTest.java does not exist at: ${testJavaFile}"
    println "PASS: Package structure validated"
    
    // Check package declarations
    println "Checking package declarations..."
    def appContent = appJavaFile.text
    assert appContent.contains("package com.snapshot.test.app;") : 
        "App.java has incorrect package declaration"
    
    def testContent = testJavaFile.text
    assert testContent.contains("package com.snapshot.test.app;") : 
        "AppTest.java has incorrect package declaration"
    println "PASS: Package declarations validated"
    
    // Check test.properties
    def testPropsFile = new File(projectDir, "src/test/resources/test.properties")
    println "Checking test properties: ${testPropsFile}"
    assert testPropsFile.exists() : "test.properties does not exist: ${testPropsFile}"
    
    def testPropsContent = testPropsFile.text
    assert testPropsContent.contains("app.test.version=3.0.0-SNAPSHOT") : 
        "test.properties missing version=3.0.0-SNAPSHOT"
    assert testPropsContent.contains("app.test.java.version=21") : 
        "test.properties missing java.version=21"
    assert testPropsContent.contains("app.test.name=snapshot-app-test") : 
        "test.properties missing name=snapshot-app-test"
    println "PASS: Test properties validated"
    
    // Verify no placeholders remain
    println "Checking for unresolved placeholders..."
    assert !testPropsContent.contains("\${") : 
        "test.properties contains unresolved placeholders: ${testPropsContent.findAll(/\$\{[^}]+\}/)}"
    assert !pomContent.contains("\${artifactId}") : 
        "POM contains unresolved \${artifactId}"
    assert !pomContent.contains("\${version}") : 
        "POM contains unresolved \${version}"
    println "PASS: No unresolved placeholders"
    
    // Check GitHub workflows exist
    println "Checking GitHub workflows..."
    def githubDir = new File(projectDir, ".github")
    assert githubDir.exists() : ".github directory does not exist: ${githubDir}"
    
    def workflowsDir = new File(projectDir, ".github/workflows")
    assert workflowsDir.exists() : "workflows directory does not exist: ${workflowsDir}"
    
    assert new File(projectDir, ".github/workflows/ci.yml").exists() : 
        "ci.yml does not exist"
    assert new File(projectDir, ".github/workflows/dependency-check.yml").exists() : 
        "dependency-check.yml does not exist"
    assert new File(projectDir, ".github/workflows/gitflow-release.yml").exists() : 
        "gitflow-release.yml does not exist"
    assert new File(projectDir, ".github/workflows/gitflow-hotfix.yml").exists() : 
        "gitflow-hotfix.yml does not exist"
    println "PASS: GitHub workflows exist"
    
    // Check .gitignore exists
    def gitignoreFile = new File(projectDir, ".gitignore")
    println "Checking .gitignore: ${gitignoreFile}"
    assert gitignoreFile.exists() : ".gitignore does not exist: ${gitignoreFile}"
    println "PASS: .gitignore exists"
    
    // Check gitflow workflows have Java 21
    println "Checking gitflow workflows for Java 21..."
    def gitflowReleaseContent = new File(projectDir, ".github/workflows/gitflow-release.yml").text
    assert gitflowReleaseContent.contains("java-version: '21'") : 
        "gitflow-release.yml does not contain java-version: '21'"
    
    def gitflowHotfixContent = new File(projectDir, ".github/workflows/gitflow-hotfix.yml").text
    assert gitflowHotfixContent.contains("java-version: '21'") : 
        "gitflow-hotfix.yml does not contain java-version: '21'"
    println "PASS: Gitflow workflows contain Java 21"
    
    println "SUCCESS: SNAPSHOT version test with custom package PASSED!"
    return true
    
} catch (AssertionError e) {
    println "ERROR: ASSERTION FAILED: ${e.message}"
    throw e
} catch (Exception e) {
    println "ERROR: UNEXPECTED ERROR: ${e.message}"
    e.printStackTrace()
    throw e
}