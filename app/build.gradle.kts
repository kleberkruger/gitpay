import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm").version(libs.versions.kotlin)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
}

repositories {
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":bank"))
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    // Use Material Design 3
    implementation(compose.material3)
    api(compose.materialIconsExtended)
    // For tests
    testImplementation(kotlin("test"))
}

compose.desktop {
    application {
        mainClass = "br.ufms.gitpay.app.GitPayAppKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = rootProject.name
            packageVersion = project.version.toString()
        }
    }
}
