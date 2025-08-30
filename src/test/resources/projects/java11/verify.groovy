// Verify Java 11 configuration
def projectDir = new File(basedir, "project/java11-app")

try {
    println "Starting verification for: ${basedir}"
    
    println "Checking project directory exists: ${projectDir}"
    assert projectDir.exists() : "Project directory does not exist: ${projectDir}"
    
    // Check POM has Java 11
    def pomFile = new File(projectDir, "pom.xml")
    println "Checking POM file: ${pomFile}"
    assert pomFile.exists() : "POM file does not exist: ${pomFile}"
    
    def pomContent = pomFile.text
    assert pomContent.contains("<maven.compiler.release>11</maven.compiler.release>") : 
        "POM missing Java 11 compiler release"
    assert pomContent.contains("<groupId>org.testjava11</groupId>") : "POM missing correct groupId"
    assert pomContent.contains("<artifactId>java11-app</artifactId>") : "POM missing correct artifactId"
    assert pomContent.contains("<version>2.3.1</version>") : "POM missing correct version"
    println "PASS: POM file validated"
    
    // Check package uses groupId as default (no package param specified)
    println "Checking package structure..."
    def appJavaFile = new File(projectDir, "src/main/java/org/testjava11/App.java")
    assert appJavaFile.exists() : "App.java does not exist at: ${appJavaFile}"
    println "PASS: Package structure validated"
    
    // Check test.properties has Java 11 and correct version
    def testPropsFile = new File(projectDir, "src/test/resources/test.properties")
    println "Checking test properties: ${testPropsFile}"
    assert testPropsFile.exists() : "test.properties does not exist: ${testPropsFile}"
    
    def testPropsContent = testPropsFile.text
    assert testPropsContent.contains("app.test.java.version=11") : 
        "test.properties missing java.version=11"
    assert testPropsContent.contains("app.test.version=2.3.1") : 
        "test.properties missing version=2.3.1"
    assert testPropsContent.contains("app.test.name=java11-app-test") : 
        "test.properties missing name=java11-app-test"
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
    
    // Check CI workflow has Java 11 in matrix
    println "Checking CI workflow content..."
    def ciWorkflow = new File(projectDir, ".github/workflows/ci.yml")
    def ciContent = ciWorkflow.text
    assert ciContent.contains("java: [ 11") || ciContent.contains("java: [11") : 
        "CI workflow does not contain Java 11 in matrix"
    println "PASS: CI workflow contains Java 11"
    
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