
tasks.register("checkNodeVersion") {
    doLast {
        rootProject.plugins.withType(org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin::class.java) {
             val ext = rootProject.extensions.getByType(org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension::class.java)
             println("🔍 DEBUG: KGP Node Version: ${ext.nodeVersion}")
             println("🔍 DEBUG: KGP Download: ${ext.download}")
        }
    }
}
