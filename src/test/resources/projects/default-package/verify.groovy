// Verify default package behavior (should use groupId)
def projectDir = new File(basedir, "project/default-pkg-app")

assert projectDir.exists()

// Check POM
def pomFile = new File(projectDir, "pom.xml")
def pomContent = pomFile.text
assert pomContent.contains("<groupId>org.mycompany</groupId>")
assert pomContent.contains("<artifactId>default-pkg-app</artifactId>")
assert pomContent.contains("<version>2.0.0</version>")

// Check that package structure uses groupId
assert new File(projectDir, "src/main/java/org/mycompany/App.java").exists()
assert new File(projectDir, "src/test/java/org/mycompany/AppTest.java").exists()

// Check package declaration
def appFile = new File(projectDir, "src/main/java/org/mycompany/App.java")
def appContent = appFile.text
assert appContent.contains("package org.mycompany;")

def testFile = new File(projectDir, "src/test/java/org/mycompany/AppTest.java")
def testContent = testFile.text
assert testContent.contains("package org.mycompany;")

// Check test.properties
def testPropsFile = new File(projectDir, "src/test/resources/test.properties")
def testPropsContent = testPropsFile.text
assert testPropsContent.contains("app.test.version=2.0.0")
assert testPropsContent.contains("app.test.name=default-pkg-app-test")

// Check GitHub workflows exist
assert new File(projectDir, ".github/workflows/ci.yml").exists()
assert new File(projectDir, ".github/workflows/dependency-check.yml").exists()
assert new File(projectDir, ".github/workflows/gitflow-release.yml").exists()
assert new File(projectDir, ".github/workflows/gitflow-hotfix.yml").exists()

// Check .gitignore exists
assert new File(projectDir, ".gitignore").exists()

println "Default package archetype generation test passed!"
return true