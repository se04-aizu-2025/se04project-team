import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.android.library")
}

kotlin {
    androidTarget()
    jvmToolchain(21)
    jvm()

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.runtime)
            implementation(libs.foundation)
            implementation(libs.material3)
            implementation(libs.ui)
            implementation(libs.components.resources)
            implementation(libs.ui.tooling.preview)
            implementation(compose.materialIconsExtended)
            implementation(projects.domain)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.ui.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.resources {
    packageOfResClass = "dotnet.sort.designsystem.generated.resources"
    publicResClass = true
}

android {
    namespace = "dotnet.sort.designsystem"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
}
dependencies {
    debugImplementation(libs.ui.tooling)
}
