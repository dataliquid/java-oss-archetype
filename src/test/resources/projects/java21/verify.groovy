// Verify Java 21 configuration
def projectDir = new File(basedir, "project/test-java21")

try {
    println "Checking project directory exists: ${projectDir}"
    assert projectDir.exists() : "Project directory does not exist: ${projectDir}"
    
    // Check POM has Java 21
    def pomFile = new File(projectDir, "pom.xml")
    println "Checking POM file: ${pomFile}"
    assert pomFile.exists() : "POM file does not exist: ${pomFile}"
    
    def pomContent = pomFile.text
    assert pomContent.contains("<maven.compiler.release>21</maven.compiler.release>") : "POM missing Java 21 compiler release"
    assert pomContent.contains("<groupId>com.example.java21</groupId>") : "POM missing correct groupId"
    assert pomContent.contains("<artifactId>test-java21</artifactId>") : "POM missing correct artifactId"
    assert pomContent.contains("<version>1.0.0</version>") : "POM missing correct version"
    println "PASS: POM file validated"
    
    // Check package uses groupId as default
    def javaFile = new File(projectDir, "src/main/java/com/example/java21/App.java")
    println "Checking Java file: ${javaFile}"
    assert javaFile.exists() : "App.java does not exist at: ${javaFile}"
    println "PASS: Java package structure validated"
    
    // Check test.properties has Java 21
    def testPropsFile = new File(projectDir, "src/test/resources/test.properties")
    println "Checking test properties: ${testPropsFile}"
    assert testPropsFile.exists() : "test.properties does not exist: ${testPropsFile}"
    
    def testPropsContent = testPropsFile.text
    assert testPropsContent.contains("app.test.java.version=21") : "test.properties missing java.version=21"
    assert testPropsContent.contains("app.test.version=1.0.0") : "test.properties missing version=1.0.0"
    assert testPropsContent.contains("app.test.name=test-java21-test") : "test.properties missing name=test-java21-test"
    println "PASS: Test properties validated"
    
    // Check GitHub workflows exist
    println "Checking GitHub workflows..."
    def githubDir = new File(projectDir, ".github")
    assert githubDir.exists() : ".github directory does not exist: ${githubDir}"
    
    def workflowsDir = new File(projectDir, ".github/workflows")
    assert workflowsDir.exists() : "workflows directory does not exist: ${workflowsDir}"
    
    def ciYml = new File(projectDir, ".github/workflows/ci.yml")
    assert ciYml.exists() : "ci.yml does not exist: ${ciYml}"
    
    def depCheckYml = new File(projectDir, ".github/workflows/dependency-check.yml")
    assert depCheckYml.exists() : "dependency-check.yml does not exist: ${depCheckYml}"
    
    def gitflowReleaseYml = new File(projectDir, ".github/workflows/gitflow-release.yml")
    assert gitflowReleaseYml.exists() : "gitflow-release.yml does not exist: ${gitflowReleaseYml}"
    
    def gitflowHotfixYml = new File(projectDir, ".github/workflows/gitflow-hotfix.yml")
    assert gitflowHotfixYml.exists() : "gitflow-hotfix.yml does not exist: ${gitflowHotfixYml}"
    println "PASS: GitHub workflows exist"
    
    // Check .gitignore exists
    def gitignoreFile = new File(projectDir, ".gitignore")
    println "Checking .gitignore: ${gitignoreFile}"
    assert gitignoreFile.exists() : ".gitignore does not exist: ${gitignoreFile}"
    println "PASS: .gitignore exists"
    
    // Check CI workflow has Java 21 in matrix
    println "Checking CI workflow content..."
    def ciContent = ciYml.text
    assert ciContent.contains("java: [ 21") || ciContent.contains("java: [21") : 
        "CI workflow does not contain Java 21 in matrix"
    println "PASS: CI workflow contains Java 21"
    
    // Check gitflow workflows have Java 21
    println "Checking gitflow-release workflow content..."
    def gitflowReleaseContent = gitflowReleaseYml.text
    assert gitflowReleaseContent.contains("java-version: '21'") : 
        "gitflow-release.yml does not contain java-version: '21'"
    println "PASS: gitflow-release contains Java 21"
    
    println "Checking gitflow-hotfix workflow content..."
    def gitflowHotfixContent = gitflowHotfixYml.text
    assert gitflowHotfixContent.contains("java-version: '21'") : 
        "gitflow-hotfix.yml does not contain java-version: '21'"
    println "PASS: gitflow-hotfix contains Java 21"
    
    println "SUCCESS: Java 21 archetype generation test PASSED!"
    return true
    
} catch (AssertionError e) {
    println "ERROR: ASSERTION FAILED: ${e.message}"
    throw e
} catch (Exception e) {
    println "ERROR: UNEXPECTED ERROR: ${e.message}"
    e.printStackTrace()
    throw e
}