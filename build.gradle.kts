allprojects {
    group = "br.ufms"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
