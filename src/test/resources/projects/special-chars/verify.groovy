// Verify handling of special characters in artifactId and version
def projectDir = new File(basedir, "project/my-special-app")

assert projectDir.exists()

// Check POM content
def pomFile = new File(projectDir, "pom.xml")
def pomContent = pomFile.text
assert pomContent.contains("<groupId>io.example.test</groupId>")
assert pomContent.contains("<artifactId>my-special-app</artifactId>")
assert pomContent.contains("<version>1.0.0-RC1</version>")
assert pomContent.contains("<maven.compiler.release>17</maven.compiler.release>")

// Check package structure (should use groupId since no package param)
assert new File(projectDir, "src/main/java/io/example/test/App.java").exists()
assert new File(projectDir, "src/test/java/io/example/test/AppTest.java").exists()

// Check package declarations
def appFile = new File(projectDir, "src/main/java/io/example/test/App.java")
def appContent = appFile.text
assert appContent.contains("package io.example.test;")

def testFile = new File(projectDir, "src/test/java/io/example/test/AppTest.java")
def testContent = testFile.text
assert testContent.contains("package io.example.test;")

// Check test.properties handles special chars correctly
def testPropsFile = new File(projectDir, "src/test/resources/test.properties")
def testPropsContent = testPropsFile.text
assert testPropsContent.contains("app.test.name=my-special-app-test")
assert testPropsContent.contains("app.test.version=1.0.0-RC1")
assert testPropsContent.contains("app.test.java.version=17")

// Check GitHub workflows exist
assert new File(projectDir, ".github/workflows/ci.yml").exists()
assert new File(projectDir, ".github/workflows/dependency-check.yml").exists()
assert new File(projectDir, ".github/workflows/gitflow-release.yml").exists()
assert new File(projectDir, ".github/workflows/gitflow-hotfix.yml").exists()

// Check .gitignore exists
assert new File(projectDir, ".gitignore").exists()

println "Special characters test passed!"
return true