import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") // 修复了缺少的括号和引号
//    id("maven-publish") // 正确应用Maven发布插件
}

group = "org.daimhim.timber"
version = "1.0-SNAPSHOT"


kotlin {
    jvm {
        withJava()
        // 将JAR配置移至jvm目标平台内
//        tasks.getByName<Jar>("jar") {
//            archiveFileName.set("${project.name}-${project.version}.jar")
//        }
        tasks.named<Jar>("jar") {
            // 使用archiveFileName替代archiveName
            archiveFileName.set("${project.name}-${project.version}.jar")
        }
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":timber"))
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "timber-multiple-platforms"
            packageVersion = "1.0.0"
        }
    }
}
// 配置Maven发布
//publishing {
//    publications {
//        create<MavenPublication>("maven") {
//            from(components["java"])
//            // 使用archiveFileName替代已弃用的archiveName
//            artifact(tasks["jar"]) {
//                classifier = ""
//                builtBy(tasks["jar"])
//            }
//        }
//    }
//}

//kotlin {
//    jvm {
//        withJava()
//        tasks.named<Jar>("jar") {
//            // 使用archiveFileName替代archiveName
//            archiveFileName.set("${project.name}-${project.version}.jar")
//        }
//    }
//}