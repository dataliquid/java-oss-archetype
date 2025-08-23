// Verify Java 21 configuration
def projectDir = new File(basedir, "project/test-java21")

assert projectDir.exists()

// Check POM has Java 21
def pomFile = new File(projectDir, "pom.xml")
def pomContent = pomFile.text
assert pomContent.contains("<maven.compiler.release>21</maven.compiler.release>")
assert pomContent.contains("<groupId>com.example.java21</groupId>")
assert pomContent.contains("<artifactId>test-java21</artifactId>")
assert pomContent.contains("<version>1.0.0</version>")

// Check package uses groupId as default
assert new File(projectDir, "src/main/java/com/example/java21/App.java").exists()

// Check test.properties has Java 21
def testPropsFile = new File(projectDir, "src/test/resources/test.properties")
def testPropsContent = testPropsFile.text
assert testPropsContent.contains("app.test.java.version=21")
assert testPropsContent.contains("app.test.version=1.0.0")
assert testPropsContent.contains("app.test.name=test-java21-test")

// Check GitHub workflows exist
assert new File(projectDir, ".github/workflows/ci.yml").exists()
assert new File(projectDir, ".github/workflows/dependency-check.yml").exists()
assert new File(projectDir, ".github/workflows/gitflow-release.yml").exists()
assert new File(projectDir, ".github/workflows/gitflow-hotfix.yml").exists()

// Check .gitignore exists
assert new File(projectDir, ".gitignore").exists()

// Check CI workflow has Java 21 in matrix
def ciWorkflow = new File(projectDir, ".github/workflows/ci.yml")
def ciContent = ciWorkflow.text
assert ciContent.contains("java: [ 21")

// Check gitflow workflows have Java 21
def gitflowRelease = new File(projectDir, ".github/workflows/gitflow-release.yml")
def gitflowReleaseContent = gitflowRelease.text
assert gitflowReleaseContent.contains("java-version: '21'")

def gitflowHotfix = new File(projectDir, ".github/workflows/gitflow-hotfix.yml")
def gitflowHotfixContent = gitflowHotfix.text
assert gitflowHotfixContent.contains("java-version: '21'")

println "Java 21 archetype generation test passed!"
return true