plugins {
    id("java")
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.slf4j)
    implementation(libs.firebase.admin)
    implementation(libs.google.api.client)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
}
