// Verify default package behavior (should use groupId)
def projectDir = new File(basedir, "project/default-pkg-app")

try {
    println "Starting verification for: ${basedir}"
    
    println "Checking project directory exists: ${projectDir}"
    assert projectDir.exists() : "Project directory does not exist: ${projectDir}"
    
    // Check POM
    def pomFile = new File(projectDir, "pom.xml")
    println "Checking POM file: ${pomFile}"
    assert pomFile.exists() : "POM file does not exist: ${pomFile}"
    
    def pomContent = pomFile.text
    assert pomContent.contains("<groupId>org.mycompany</groupId>") : "POM missing correct groupId"
    assert pomContent.contains("<artifactId>default-pkg-app</artifactId>") : "POM missing correct artifactId"
    assert pomContent.contains("<version>2.0.0</version>") : "POM missing correct version"
    println "PASS: POM file validated"
    
    // Check that package structure uses groupId
    println "Checking package structure uses groupId..."
    def appJavaFile = new File(projectDir, "src/main/java/org/mycompany/App.java")
    assert appJavaFile.exists() : "App.java does not exist at: ${appJavaFile}"
    
    def testJavaFile = new File(projectDir, "src/test/java/org/mycompany/AppTest.java")
    assert testJavaFile.exists() : "AppTest.java does not exist at: ${testJavaFile}"
    println "PASS: Package structure validated (using groupId as default)"
    
    // Check package declaration
    println "Checking package declarations..."
    def appContent = appJavaFile.text
    assert appContent.contains("package org.mycompany;") : 
        "App.java has incorrect package declaration"
    
    def testContent = testJavaFile.text
    assert testContent.contains("package org.mycompany;") : 
        "AppTest.java has incorrect package declaration"
    println "PASS: Package declarations validated"
    
    // Check test.properties
    def testPropsFile = new File(projectDir, "src/test/resources/test.properties")
    println "Checking test properties: ${testPropsFile}"
    assert testPropsFile.exists() : "test.properties does not exist: ${testPropsFile}"
    
    def testPropsContent = testPropsFile.text
    assert testPropsContent.contains("app.test.version=2.0.0") : 
        "test.properties missing version=2.0.0"
    assert testPropsContent.contains("app.test.name=default-pkg-app-test") : 
        "test.properties missing name=default-pkg-app-test"
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