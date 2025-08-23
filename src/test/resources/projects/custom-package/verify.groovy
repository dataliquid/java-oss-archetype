// Verify custom package parameter works correctly
def projectDir = new File(basedir, "project/custom-pkg-app")

assert projectDir.exists()

// Check POM has correct groupId and artifactId
def pomFile = new File(projectDir, "pom.xml")
def pomContent = pomFile.text
assert pomContent.contains("<groupId>com.mycompany</groupId>")
assert pomContent.contains("<artifactId>custom-pkg-app</artifactId>")
assert pomContent.contains("<version>1.5.0</version>")
assert pomContent.contains("<maven.compiler.release>17</maven.compiler.release>")

// Check that package structure uses custom package, NOT groupId
assert new File(projectDir, "src/main/java/org/custompackage/application/App.java").exists()
assert new File(projectDir, "src/test/java/org/custompackage/application/AppTest.java").exists()

// Should NOT have the groupId package structure
assert !new File(projectDir, "src/main/java/com/mycompany").exists()

// Check package declaration in source files
def appFile = new File(projectDir, "src/main/java/org/custompackage/application/App.java")
def appContent = appFile.text
assert appContent.contains("package org.custompackage.application;")
assert !appContent.contains("package com.mycompany;")

def testFile = new File(projectDir, "src/test/java/org/custompackage/application/AppTest.java")
def testContent = testFile.text
assert testContent.contains("package org.custompackage.application;")

// Check test.properties
def testPropsFile = new File(projectDir, "src/test/resources/test.properties")
def testPropsContent = testPropsFile.text
assert testPropsContent.contains("app.test.java.version=17")
assert testPropsContent.contains("app.test.version=1.5.0")
assert testPropsContent.contains("app.test.name=custom-pkg-app-test")

// Check GitHub workflows exist
assert new File(projectDir, ".github/workflows/ci.yml").exists()
assert new File(projectDir, ".github/workflows/dependency-check.yml").exists()
assert new File(projectDir, ".github/workflows/gitflow-release.yml").exists()
assert new File(projectDir, ".github/workflows/gitflow-hotfix.yml").exists()

// Check .gitignore exists
assert new File(projectDir, ".gitignore").exists()

println "Custom package test passed - package parameter correctly overrides groupId!"
return true