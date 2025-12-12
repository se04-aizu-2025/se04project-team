import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLint)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {

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
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }

//    // Target declarations - add or remove as needed below. These define
//    // which platforms this KMP module supports.
//    // See: https://kotlinlang.org/docs/multiplatform-discover-project.html#targets
//    androidLibrary {
//        namespace = "com.segnities007.ui"
//        compileSdk = 36
//        minSdk = 26
//
//        withHostTestBuilder {
//        }
//
//        withDeviceTestBuilder {
//            sourceSetTreeName = "test"
//        }.configure {
//            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        }
//    }
//
//    // For iOS targets, this is also where you should
//    // configure native binary output. For more information, see:
//    // https://kotlinlang.org/docs/multiplatform-build-native-binaries.html#build-xcframeworks
//
//    // A step-by-step guide on how to include this library in an XCode
//    // project can be found here:
//    // https://developer.android.com/kotlin/multiplatform/migrate
//
//    // Source set declarations.
//    // Declaring a target automatically creates a source set with the same name. By default, the
//    // Kotlin Gradle Plugin creates additional source sets that depend on each other, since it is
//    // common to share sources between related targets.
//    // See: https://kotlinlang.org/docs/multiplatform-hierarchy.html
//    sourceSets {
//        commonMain {
//            dependencies {
//                implementation(libs.kotlin.stdlib)
//                // Add KMP dependencies here
//            }
//        }
//
//        commonTest {
//            dependencies {
//                implementation(libs.kotlin.test)
//            }
//        }
//    }
}

compose.desktop {
    application {
        mainClass = "dotnet.sort.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dotnet.sort"
            packageVersion = "1.0.0"
        }
    }
}
