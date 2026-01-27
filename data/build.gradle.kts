import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.sqldelight)
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
        commonMain {
            dependencies {
                implementation(projects.domain)
                implementation(libs.kotlin.stdlib)
                implementation(libs.koin.core)
                implementation(libs.koin.annotations)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.coroutines)
                implementation(libs.sqldelight.async)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        jvmMain {
            dependencies {
                implementation(libs.sqldelight.driver.jvm)
            }
        }

        jsMain {
            dependencies {
                implementation(libs.sqldelight.driver.js)
                implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.2.1"))
                implementation(npm("sql.js", "1.8.0"))
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            }
        }

        wasmJsMain {
            dependencies {
                implementation(libs.sqldelight.driver.wasm.js)
                implementation(libs.kotlinx.browser)
                implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.2.1"))
                implementation(npm("sql.js", "1.8.0"))
                implementation(devNpm("copy-webpack-plugin", "9.1.0"))
            }
        }
    }
}

sqldelight {
    databases {
        create("DnsortDatabase") {
            packageName.set("dotnet.sort.data")
            generateAsync.set(true)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.koin.kspCompiler)
    add("kspJvm", libs.koin.kspCompiler)
    add("kspJs", libs.koin.kspCompiler)
    add("kspWasmJs", libs.koin.kspCompiler)
}

tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCommonCompile>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}
