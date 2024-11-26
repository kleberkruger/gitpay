plugins {
    java
    alias(libs.plugins.springframework)
    alias(libs.plugins.dependency.management)
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.springboot.data.jpa)
    implementation(libs.springboot.validation)
    implementation(libs.springboot.web)
    implementation(libs.springboot.hateoas)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    runtimeOnly(libs.postgres)
    testImplementation(libs.springboot.test)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}
