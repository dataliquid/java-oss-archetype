// Verify handling of special characters in artifactId and version
def projectDir = new File(basedir, "project/my-special-app")

try {
    println "Starting verification for: ${basedir}"
    
    println "Checking project directory exists: ${projectDir}"
    assert projectDir.exists() : "Project directory does not exist: ${projectDir}"
    
    // Check POM content
    def pomFile = new File(projectDir, "pom.xml")
    println "Checking POM file: ${pomFile}"
    assert pomFile.exists() : "POM file does not exist: ${pomFile}"
    
    def pomContent = pomFile.text
    assert pomContent.contains("<groupId>io.example.test</groupId>") : "POM missing correct groupId"
    assert pomContent.contains("<artifactId>my-special-app</artifactId>") : "POM missing correct artifactId"
    assert pomContent.contains("<version>1.0.0-RC1</version>") : "POM missing correct version"
    assert pomContent.contains("<maven.compiler.release>17</maven.compiler.release>") : "POM missing Java 17 compiler release"
    println "PASS: POM file validated"
    
    // Check package structure (should use groupId since no package param)
    println "Checking package structure..."
    def appJavaFile = new File(projectDir, "src/main/java/io/example/test/App.java")
    assert appJavaFile.exists() : "App.java does not exist at: ${appJavaFile}"
    
    def testJavaFile = new File(projectDir, "src/test/java/io/example/test/AppTest.java")
    assert testJavaFile.exists() : "AppTest.java does not exist at: ${testJavaFile}"
    println "PASS: Package structure validated"
    
    // Check package declarations
    println "Checking package declarations..."
    def appContent = appJavaFile.text
    assert appContent.contains("package io.example.test;") : 
        "App.java has incorrect package declaration"
    
    def testContent = testJavaFile.text
    assert testContent.contains("package io.example.test;") : 
        "AppTest.java has incorrect package declaration"
    println "PASS: Package declarations validated"
    
    // Check test.properties handles special chars correctly
    def testPropsFile = new File(projectDir, "src/test/resources/test.properties")
    println "Checking test properties: ${testPropsFile}"
    assert testPropsFile.exists() : "test.properties does not exist: ${testPropsFile}"
    
    def testPropsContent = testPropsFile.text
    assert testPropsContent.contains("app.test.name=my-special-app-test") : 
        "test.properties missing name=my-special-app-test"
    assert testPropsContent.contains("app.test.version=1.0.0-RC1") : 
        "test.properties missing version=1.0.0-RC1"
    assert testPropsContent.contains("app.test.java.version=17") : 
        "test.properties missing java.version=17"
    println "PASS: Test properties validated with special characters"
    
    // Check GitHub workflows exist
    println "Checking GitHub workflows..."
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