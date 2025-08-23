// Post-generation script to rename hidden files/directories
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

// Get the generated project directory
Path projectPath = Paths.get(request.outputDirectory, request.artifactId)

// Rename __gitignore__ to .gitignore
Path gitignoreSrc = projectPath.resolve("__gitignore__")
Path gitignoreDst = projectPath.resolve(".gitignore")
if (Files.exists(gitignoreSrc)) {
    Files.move(gitignoreSrc, gitignoreDst)
}

// Rename __github__ to .github
Path githubSrc = projectPath.resolve("__github__")
Path githubDst = projectPath.resolve(".github")
if (Files.exists(githubSrc)) {
    Files.move(githubSrc, githubDst)
}