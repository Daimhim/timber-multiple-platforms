plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group = "org.daimhim.timber"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    implementation(project(":timber"))
    implementation("androidx.activity:activity-compose:1.5.0")
}

android {
    compileSdkVersion(31)
    defaultConfig {
        applicationId = "org.daimhim.timber.android"
        minSdk = 22
        targetSdk = 31
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}