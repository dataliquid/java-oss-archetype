// Verify custom package parameter works correctly
def projectDir = new File(basedir, "project/custom-pkg-app")

try {
    println "Starting verification for: ${basedir}"
    
    println "Checking project directory exists: ${projectDir}"
    assert projectDir.exists() : "Project directory does not exist: ${projectDir}"
    
    // Check POM has correct groupId and artifactId
    def pomFile = new File(projectDir, "pom.xml")
    println "Checking POM file: ${pomFile}"
    assert pomFile.exists() : "POM file does not exist: ${pomFile}"
    
    def pomContent = pomFile.text
    assert pomContent.contains("<groupId>com.mycompany</groupId>") : "POM missing correct groupId"
    assert pomContent.contains("<artifactId>custom-pkg-app</artifactId>") : "POM missing correct artifactId"
    assert pomContent.contains("<version>1.5.0</version>") : "POM missing correct version"
    assert pomContent.contains("<maven.compiler.release>17</maven.compiler.release>") : "POM missing Java 17 compiler release"
    println "PASS: POM file validated"
    
    // Check that package structure uses custom package, NOT groupId
    println "Checking custom package structure..."
    def appJavaFile = new File(projectDir, "src/main/java/org/custompackage/application/App.java")
    assert appJavaFile.exists() : "App.java does not exist at: ${appJavaFile}"
    
    def testJavaFile = new File(projectDir, "src/test/java/org/custompackage/application/AppTest.java")
    assert testJavaFile.exists() : "AppTest.java does not exist at: ${testJavaFile}"
    
    // Should NOT have the groupId package structure
    def wrongPackageDir = new File(projectDir, "src/main/java/com/mycompany")
    assert !wrongPackageDir.exists() : "Wrong package structure exists (should use custom package, not groupId): ${wrongPackageDir}"
    println "PASS: Custom package structure validated"
    
    // Check package declaration in source files
    println "Checking package declarations..."
    def appContent = appJavaFile.text
    assert appContent.contains("package org.custompackage.application;") : 
        "App.java has incorrect package declaration"
    assert !appContent.contains("package com.mycompany;") : 
        "App.java contains wrong package declaration (com.mycompany)"
    
    def testContent = testJavaFile.text
    assert testContent.contains("package org.custompackage.application;") : 
        "AppTest.java has incorrect package declaration"
    println "PASS: Package declarations validated"
    
    // Check test.properties
    def testPropsFile = new File(projectDir, "src/test/resources/test.properties")
    println "Checking test properties: ${testPropsFile}"
    assert testPropsFile.exists() : "test.properties does not exist: ${testPropsFile}"
    
    def testPropsContent = testPropsFile.text
    assert testPropsContent.contains("app.test.java.version=17") : 
        "test.properties missing java.version=17"
    assert testPropsContent.contains("app.test.version=1.5.0") : 
        "test.properties missing version=1.5.0"
    assert testPropsContent.contains("app.test.name=custom-pkg-app-test") : 
        "test.properties missing name=custom-pkg-app-test"
    println "PASS: Test properties validated"
    
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