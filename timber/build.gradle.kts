//import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
//import com.vanniktech.maven.publish.JavadocJar
//import com.vanniktech.maven.publish.KotlinMultiplatform

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
//    id("com.vanniktech.maven.publish.base") version "0.25.3"
}

group = "timber.multiplatform.log"
version = "2.5.0-SNAPSHOT"

kotlin {
    android(){
        publishLibraryVariants("release")
    }
    jvm("desktop") {
        jvmToolchain(11)
    }
    sourceSets {
        val commonMain by getting {
            dependencies {

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependencies {
//                compileOnly("commons-io:commons-io:2.11.0")
            }
        }
        val desktopTest by getting
    }
}

android {
    compileSdkVersion(33)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(22)
        targetSdkVersion(33)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
publishing {
    repositories {
        maven {
            url = uri("../repo")
        }
    }
}