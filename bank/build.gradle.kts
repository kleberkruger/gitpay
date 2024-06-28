plugins {
    id("java")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
}
