import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.android.library")
    alias(libs.plugins.ksp)
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
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(libs.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeViewModel)
            implementation(libs.koin.annotations)
            implementation(projects.domain)
            implementation(projects.presentation.common)
            implementation(projects.presentation.designsystem)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmTest.dependencies {
            implementation(libs.junit)
            implementation(libs.kotlin.testJunit)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.koin.kspCompiler)
    add("kspJvm", libs.koin.kspCompiler)
    add("kspJs", libs.koin.kspCompiler)
    add("kspWasmJs", libs.koin.kspCompiler)
}

compose.resources {
    packageOfResClass = "dotnet.sort.presentation.feature.quiz.generated.resources"
    publicResClass = true
}

tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompile>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

android {
    namespace = "dotnet.sort.presentation.feature.quiz"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
}
