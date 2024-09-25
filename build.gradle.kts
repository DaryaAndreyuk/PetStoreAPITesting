plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("io.rest-assured:rest-assured:5.5.0")
    testImplementation("org.testng:testng:7.10.2")
    implementation("org.hamcrest:hamcrest:2.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    testImplementation("org.assertj:assertj-core:3.26.3")
}

tasks.test {
    useTestNG()
}
