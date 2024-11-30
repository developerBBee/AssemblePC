import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.dom.document
import kotlinx.html.script

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
//    alias(libs.plugins.kobwebx.markdown)
    alias(libs.plugins.kotlinx.serialization)
}

group = "bbee.developer.jp.assemble_pc"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")

            document {
                lang.set("ja")
            }

            head.add {
//                link(rel = "preconnect", href = "https://fonts.googleapis.com")
//                link(rel = "preconnect", href = "https://fonts.gstatic.com") { attributes["crossorigin"] = "" }
//                link(
//                    href = "https://fonts.googleapis.com/css2?family=Roboto&display=swap",
//                    rel = "stylesheet"
//                )
                script {
                    src = "https://platform.twitter.com/widgets.js"
                }
            }
        }
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
            implementation(libs.silk.icons.fa)
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
            implementation(libs.ktor.client.core.jvm)
            implementation(libs.ktor.client.cio.jvm)
            implementation(libs.koin.core.jvm)
        }

        jvmTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
