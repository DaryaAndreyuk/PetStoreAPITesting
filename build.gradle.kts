plugins {
    id("java")
    id("io.qameta.allure") version "2.11.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.testng:testng:7.7.0")
    implementation("org.assertj:assertj-core:3.19.0")
    testImplementation("io.qameta.allure:allure-testng:2.21.0")

    implementation("org.hamcrest:hamcrest:2.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    implementation("io.rest-assured:rest-assured:5.4.0")
    implementation("io.qameta.allure:allure-rest-assured:2.21.0")
    implementation("org.aspectj:aspectjweaver:1.9.21")
}

tasks.test {
    useTestNG()
    systemProperty("allure.results.directory", layout.buildDirectory.dir("allure-results").get().asFile)
}

allure {
    adapter.autoconfigure.set(true)
    version.set("2.21.0")
}

if (tasks.findByName("allureServe") == null) {
    tasks.register("allureServe") {
        group = "verification"
        description = "Serves the Allure report"
        doLast {
            exec {
                commandLine("allure", "serve", layout.buildDirectory.dir("allure-results").get().asFile.absolutePath)
            }
        }
    }
}