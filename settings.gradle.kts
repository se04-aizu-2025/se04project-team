rootProject.name = "dotnet"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":composeApp")
include(":presentation:common")
include(":presentation:designsystem")
include(":presentation:navigation")
include(":presentation:feature:home")
include(":presentation:feature:sort")
include(":presentation:feature:learn")
include(":presentation:feature:compare")
include(":presentation:feature:quiz")
include(":presentation:feature:settings")
include(":domain")
include(":data")
