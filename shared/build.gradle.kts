import com.varabyte.kobweb.gradle.library.util.configAsKobwebLibrary

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kobweb.library)
    alias(libs.plugins.kotlinx.serialization)
    id("com.android.library")
}

version = "1.0-SNAPSHOT"
group = "jp.developer.bbee.assemblepc.shared"

kotlin {
    configAsKobwebLibrary(includeServer = true)

    js(IR) { browser() }
    jvm()
    androidTarget()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.serialization)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.kobweb.core)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.cio.jvm)
            }
        }

        androidMain.dependencies {}
    }
}

android {
    compileSdk = 35
    namespace = "jp.developer.bbee.assemblepc.shared"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
    packaging {
        resources {
            excludes += "/META-INF/**"
        }
    }
}
