plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of(11))
    }

    android()
    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                implementation("io.ktor:ktor-client-core:2.3.1")
                implementation("io.ktor:ktor-client-serialization:1.6.8")
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.compose.ui:ui:1.6.8")
                implementation("androidx.compose.material:material:1.6.8")
                implementation("androidx.compose.ui:ui-tooling:1.6.8")
                implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
                implementation("com.google.accompanist:accompanist-permissions:0.26.2-beta")
                implementation("io.github.sceneview:sceneview:2.0.0")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("org.jetbrains.compose.runtime:runtime:1.4.0")
                implementation("org.jetbrains.compose.foundation:foundation:1.4.0")
                implementation("org.jetbrains.compose.material:material:1.4.0")
            }
        }
    }
}

android {
    compileSdk = 34
    namespace = "com.example.mybirdapp"  // Укажите ваше пространство имен здесь
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 34
    }
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}
