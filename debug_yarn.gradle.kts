
tasks.register("checkYarnConfig") {
    doLast {
        try {
            val yarn = rootProject.extensions.findByType(org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension::class.java)
            println("🔍 DEBUG: Yarn Extension Found: ${yarn != null}")
            println("🔍 DEBUG: Current Report Mode: ${yarn?.yarnLockMismatchReport}")
        } catch (e: Exception) {
            println("🔍 DEBUG: Failed to check yarn config: $e")
        }
    }
}
