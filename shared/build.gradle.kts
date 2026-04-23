plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.jetbrainsCompose)
    kotlin("native.cocoapods")

}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
        }
    }

    cocoapods {
        summary = "Shared module"
        homepage = "Link"
        version = "1.0.0"   // ✅ ADD THIS LINE (MANDATORY)

        ios.deploymentTarget = "14.1"

        framework {
            baseName = "shared"
        }
        podfile = project.file("../iosApp/Podfile")
    }


    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        binaries.framework {
            baseName = "shared"
        }
    }
    sourceSets {

        val commonMain by getting {
//            resources.srcDir("src/commonMain/composeResources/drawable")
            dependencies {
//                implementation(libs.compose.ui)
                implementation(compose.ui)
                implementation(compose.runtime)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.components.resources)
                implementation(libs.lifecycle.runtime.compose)

                implementation(libs.ktor.core)
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.logging)

                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(compose.materialIconsExtended)
                implementation(compose.components.resources)
                implementation(libs.kamel.image)
                implementation(libs.settings)
                implementation(libs.settings.no.arg)
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.screenmodel)
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.androidx.compose)

            }
        }
        commonTest.dependencies {
            implementation(libs.koin.test)
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.ktor.android)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.ktor.serialization)
                implementation(libs.koin.androidx.compose)

            }
        }
        val iosMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
        iosX64()
        iosArm64()
        iosSimulatorArm64()
        val iosX64Main by getting { dependsOn(iosMain) }
        val iosArm64Main by getting { dependsOn(iosMain) }
        val iosSimulatorArm64Main by getting { dependsOn(iosMain) }


    }
}
compose {
    resources {
        publicResClass = true
    }
}

android {
    namespace = "com.example.recipeapp"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

}
dependencies {
    implementation(libs.transport.runtime)
    implementation(libs.ads.mobile.sdk)
}


