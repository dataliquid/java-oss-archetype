// Verify SNAPSHOT version and custom package
def projectDir = new File(basedir, "project/snapshot-app")

assert projectDir.exists()

// Check POM content
def pomFile = new File(projectDir, "pom.xml")
def pomContent = pomFile.text
assert pomContent.contains("<groupId>com.snapshot.test</groupId>")
assert pomContent.contains("<artifactId>snapshot-app</artifactId>")
assert pomContent.contains("<version>3.0.0-SNAPSHOT</version>")
assert pomContent.contains("<maven.compiler.release>21</maven.compiler.release>")

// Check package structure uses custom package parameter
assert new File(projectDir, "src/main/java/com/snapshot/test/app/App.java").exists()
assert new File(projectDir, "src/test/java/com/snapshot/test/app/AppTest.java").exists()

// Check package declarations
def appFile = new File(projectDir, "src/main/java/com/snapshot/test/app/App.java")
def appContent = appFile.text
assert appContent.contains("package com.snapshot.test.app;")

def testFile = new File(projectDir, "src/test/java/com/snapshot/test/app/AppTest.java")
def testContent = testFile.text
assert testContent.contains("package com.snapshot.test.app;")

// Check test.properties
def testPropsFile = new File(projectDir, "src/test/resources/test.properties")
def testPropsContent = testPropsFile.text
assert testPropsContent.contains("app.test.version=3.0.0-SNAPSHOT")
assert testPropsContent.contains("app.test.java.version=21")
assert testPropsContent.contains("app.test.name=snapshot-app-test")

// Verify no placeholders remain
assert !testPropsContent.contains("\${")
assert !pomContent.contains("\${artifactId}")
assert !pomContent.contains("\${version}")

// Check GitHub workflows exist
assert new File(projectDir, ".github/workflows/ci.yml").exists()
assert new File(projectDir, ".github/workflows/dependency-check.yml").exists()
assert new File(projectDir, ".github/workflows/gitflow-release.yml").exists()
assert new File(projectDir, ".github/workflows/gitflow-hotfix.yml").exists()

// Check .gitignore exists
assert new File(projectDir, ".gitignore").exists()

println "SNAPSHOT version test with custom package passed!"
return true