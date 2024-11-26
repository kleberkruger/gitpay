allprojects {
    group = "br.ufms"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    plugins.withType<JavaPlugin> {
        extensions.configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_22
            targetCompatibility = JavaVersion.VERSION_22
        }
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
