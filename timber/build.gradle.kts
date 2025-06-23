plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
}

group = "timber.multiplatform.log"
version = "2.5.6-SNAPSHOT"

kotlin {
    android(){
        publishLibraryVariants("release")
    }
    jvm("desktop") {
//        jvmToolchain(11)
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
    compileSdkVersion(31)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 22
        targetSdk = 31
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
//publishing {
//    repositories {
//        maven {
//            url = uri("../repo")
//        }
//    }
//}