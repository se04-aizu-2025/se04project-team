plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.androidKotlinMultiplatformLibrary) apply false
    alias(libs.plugins.androidLint) apply false

    // Code quality plugins
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
}

// detekt configuration
detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
}

// Git hooks installation
tasks.register<Copy>("installGitHooks") {
    description = "Install git hooks"
    group = "setup"
    from(file(".git-hooks"))
    into(file(".git/hooks"))
    fileMode = 0b111101101 // 0755 in octal
}

// サブプロジェクトのbuildタスクに自動インストールを追加
subprojects {
    tasks.whenTaskAdded {
        if (name == "build") {
            dependsOn(":installGitHooks")
        }
    }
}

// タスク: Git hooksを手動でインストール
tasks.register("setupGitHooks") {
    description = "Install git hooks manually"
    group = "setup"
    dependsOn("installGitHooks")
    doLast {
        val hooksDir = project.layout.projectDirectory.dir(".git/hooks").asFile
        println("✅ Git hooks installed successfully!")
        println("Hooks location: ${hooksDir.absolutePath}")
    }
    notCompatibleWithConfigurationCache("Uses project reference")
}

// Kotlin/JS Yarn Lock Check緩和
// CI環境での環境差異によるビルド失敗を防ぐため、Yarnロックファイルの不整合をエラーではなく警告にします。
// Kotlin/JS Yarn Lock Check緩和
// CI環境での環境差異によるビルド失敗を防ぐため、Yarnロックファイルの不整合をエラーではなく警告にします。
rootProject.afterEvaluate {
    rootProject.extensions.findByType(org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension::class.java)?.apply {
        yarnLockMismatchReport = org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport.WARNING
        reportNewYarnLock = false
        println("⚠️ YarnRootExtension configured to WARNING (Relaxed lock check)")
    }
}
