plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation ("io.rest-assured:rest-assured:5.5.0")
    testImplementation("org.testng:testng:7.10.2")
    testImplementation("org.hamcrest:hamcrest:2.2")
}

tasks.test {
    useTestNG()
}