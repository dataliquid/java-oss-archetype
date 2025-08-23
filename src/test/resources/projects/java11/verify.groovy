// Verify Java 11 configuration
def projectDir = new File(basedir, "project/java11-app")

assert projectDir.exists()

// Check POM has Java 11
def pomFile = new File(projectDir, "pom.xml")
def pomContent = pomFile.text
assert pomContent.contains("<maven.compiler.release>11</maven.compiler.release>")
assert pomContent.contains("<groupId>org.testjava11</groupId>")
assert pomContent.contains("<artifactId>java11-app</artifactId>")
assert pomContent.contains("<version>2.3.1</version>")

// Check package uses groupId as default (no package param specified)
assert new File(projectDir, "src/main/java/org/testjava11/App.java").exists()

// Check test.properties has Java 11 and correct version
def testPropsFile = new File(projectDir, "src/test/resources/test.properties")
def testPropsContent = testPropsFile.text
assert testPropsContent.contains("app.test.java.version=11")
assert testPropsContent.contains("app.test.version=2.3.1")
assert testPropsContent.contains("app.test.name=java11-app-test")

// Check GitHub workflows exist
assert new File(projectDir, ".github/workflows/ci.yml").exists()
assert new File(projectDir, ".github/workflows/dependency-check.yml").exists()
assert new File(projectDir, ".github/workflows/gitflow-release.yml").exists()
assert new File(projectDir, ".github/workflows/gitflow-hotfix.yml").exists()

// Check .gitignore exists
assert new File(projectDir, ".gitignore").exists()

// Check CI workflow has Java 11 in matrix
def ciWorkflow = new File(projectDir, ".github/workflows/ci.yml")
def ciContent = ciWorkflow.text
assert ciContent.contains("java: [ 11")

println "Java 11 test passed!"
return true