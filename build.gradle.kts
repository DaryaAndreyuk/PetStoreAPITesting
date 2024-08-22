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
    testImplementation ("io.rest-assured:json-path:5.5.0")
    testImplementation ("io.rest-assured:xml-path:5.5.0")
    testImplementation (platform("org.junit:junit-bom:5.10.0"))
    testImplementation ("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}