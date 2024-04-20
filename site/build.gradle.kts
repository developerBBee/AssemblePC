import com.varabyte.kobweb.gradle.application.extensions.AppBlock.LegacyRouteRedirectStrategy
import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
    alias(libs.plugins.kotlinx.serialization)
}

group = "bbee.developer.jp.assemble_pc"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
        }

        // Only legacy sites need this set. Sites built after 0.16.0 should default to DISALLOW.
        // See https://github.com/varabyte/kobweb#legacy-routes for more information.
        legacyRouteRedirectStrategy.set(LegacyRouteRedirectStrategy.DISALLOW)
    }
}

kotlin {
    // This example is frontend only. However, for a fullstack app, you can uncomment the includeServer parameter
    // and the `jvmMain` source set below.
    configAsKobwebApplication("assemble_pc" , includeServer = true)

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(libs.kotlinx.serialization)
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(libs.firebase.kotlin.auth)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            // This default template uses built-in SVG icons, but what's available is limited.
            // Uncomment the following if you want access to a large set of font-awesome icons:
            // implementation(libs.silk.icons.fa)
            implementation(libs.kobwebx.markdown)
            implementation(libs.kotlinx.serialization)
        }

        // Uncomment the following if you pass `includeServer = true` into the `configAsKobwebApplication` call.
        jvmMain.dependencies {
            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime
            implementation(libs.exposed.core)
            implementation(libs.exposed.crypt)
            implementation(libs.exposed.dao)
            implementation(libs.exposed.jdbc)
            implementation(libs.exposed.json)
            implementation(libs.exposed.kotlin.datetime)
            implementation(libs.exposed.money)
            implementation(libs.firebase.admin)
            implementation(libs.h2.database)
            implementation(libs.kotlinx.serialization)
        }
    }
}
